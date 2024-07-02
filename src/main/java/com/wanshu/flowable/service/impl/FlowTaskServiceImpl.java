package com.wanshu.flowable.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wanshu.flowable.config.CustomProcessDiagramGenerator;
import com.wanshu.flowable.domain.dto.FlowTaskDto;
import com.wanshu.flowable.domain.vo.FlowTaskVo;
import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.IFlowTaskService;
import com.wanshu.flowable.service.ImageService;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.api.history.HistoricTaskInstanceQuery;
import org.flowable.variable.api.persistence.entity.VariableInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengc
 * @description 我的待办 服务实现
 * @date 2024/06/11
 */
@Slf4j
@Service
public class FlowTaskServiceImpl extends FlowServiceFactory implements IFlowTaskService {

    @Resource
    IUserService userService;
    @Resource
    ImageService imageService;

    /**
     * 我的待办
     *
     * @param pageVo 我的待办
     */
    @Override
    public void myToDoTaskList(PageUtils pageVo) {
        // 获取当前登录用户
        String userName = WebUtils.loginUserName();
        // 查询我的待办
        TaskQuery taskQuery = taskService.createTaskQuery()
                // 激活的任务
                .active()
                .taskAssignee(userName)
                .orderByTaskCreateTime()
                .desc();
        // 用户名称模糊查询
        if (Objects.nonNull(pageVo.getKey())) {
            taskQuery.taskNameLike("%" + pageVo.getKey() + "%");
        }
        int currPage = pageVo.getCurrPage();
        int pageSize = pageVo.getPageSize();
        List<Task> tasks = taskQuery.listPage((currPage - 1) * pageSize, currPage * pageSize);
        // 封装数据
        int count = (int) taskQuery.count();
        pageVo.setTotalCount(count);
        pageVo.setTotalPage((count + 1) / pageSize + 1);


        List<FlowTaskDto> flowTaskDtos = tasks.stream().map(task -> {
            // 查询流程定义信息
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            // 查询流程发起人的信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();

            Map<String, VariableInstance> variableInstances = taskService.getVariableInstances(task.getId());
            // 查询任务的发起人
            String startUserId = historicProcessInstance.getStartUserId();
            // 查询用户信息
            User user = userService.queryByUserName(startUserId);
            // 封装数据
            return FlowTaskDto.builder()
                    .taskId(task.getId())
                    .assigneeId((long) user.getId())
                    .assigneeName(task.getAssignee())
                    .createTime(task.getCreateTime())
                    .processInstanceId(task.getProcessInstanceId())
                    .taskName(task.getName())
                    .taskExecutionId(task.getExecutionId())
                    .processDefinitionKey(processDefinition.getKey())
                    .taskDefinitionKey(task.getTaskDefinitionKey())
                    .processDefinitionId(processDefinition.getId())
                    .processDefinitionName(processDefinition.getName())
                    .processDefinitionVersion(processDefinition.getVersion())
                    // 绑定对应的流程变量
                    .variables(variableInstances)
                    .deploymentId(processDefinition.getDeploymentId())
                    .startUserId(startUserId)
                    .startUserName(user.getNickName())
                    .startTime(historicProcessInstance.getStartTime())
                    .build();
        }).collect(Collectors.toList());
        log.info("我的待办数据：{}", flowTaskDtos);
        // 设置数据
        pageVo.setList(flowTaskDtos);

    }

    @Override
    public byte[] diagram(String processId) {
        try {
            return imageService.generateImageByProcInstId(processId);

        } catch (Exception e) {
            log.error("生成流程图失败", e);
        }
        return new byte[0];
    }

    /**
     * 通过任务id查询流程实例
     *
     * @param taskVo 审批对象
     * @return {@link Boolean }
     */
    @Override
    public boolean complete(FlowTaskVo taskVo) {
        try {
            // 当前要处理的任务
            Task task = taskService.createTaskQuery().taskId(taskVo.getTaskId()).singleResult();
            taskService.addComment(task.getId(), task.getProcessInstanceId(), taskVo.getComment());
            //完成任务 --> 指定下一个节点的处理人
            Map<String, Object> varbies = new HashMap<>();
            varbies.put("assigne2", "lisi");
            varbies.put("approvd", taskVo.getApprovd());
            taskService.complete(task.getId(), varbies);
            return true;
        } catch (Exception e) {
            log.error("审批异常：", e);
        }
        return false;
    }

    /**
     * 查询已办
     *
     * @param vo 查询条件
     */
    @Override
    public void finishedList(PageUtils vo) {
        // 获取当前登录用户
        String userName = WebUtils.loginUserName();
        int currPage = vo.getCurrPage();
        int pageSize = vo.getPageSize();
        // 查询已经完成的历史任务
        HistoricTaskInstanceQuery historicTaskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .includeCaseVariables()
                .finished()
                .taskAssignee(userName)
                .orderByHistoricTaskInstanceStartTime()
                .desc();
        if (StringUtils.isNotBlank(vo.getKey())) {
            historicTaskInstanceQuery.processInstanceId(vo.getKey());
        }

        List<HistoricTaskInstance> historicTaskInstances = historicTaskInstanceQuery
                .listPage((currPage - 1) * pageSize, pageSize);
        int count = (int) historicTaskInstanceQuery.count();
        vo.setTotalCount(count);
        vo.setTotalPage((count + 1) / pageSize + 1);

        List<FlowTaskDto> flowTaskDtos = historicTaskInstances.stream().map(task -> {
            // 查询流程定义信息
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(task.getProcessDefinitionId())
                    .singleResult();
            // 查询流程发起人的信息
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            // 查询任务的发起人
            String startUserId = historicProcessInstance.getStartUserId();
            // 查询用户信息
            User user = userService.queryByUserName(startUserId);
            // 耗时
            long timeConsuming = task.getDurationInMillis() / 1000;
            String timeConsumingStr = getTimeConsumingStr(timeConsuming);


            // 封装数据
            return FlowTaskDto.builder()
                    .taskId(task.getId())
                    .assigneeId((long) user.getId())
                    .assigneeName(task.getAssignee())
                    .createTime(task.getCreateTime())
                    .processInstanceId(task.getProcessInstanceId())
                    .taskName(task.getName())
                    .taskExecutionId(task.getExecutionId())
                    .processDefinitionKey(processDefinition.getKey())
                    .taskDefinitionKey(task.getTaskDefinitionKey())
                    .processDefinitionId(processDefinition.getId())
                    .processDefinitionName(processDefinition.getName())
                    .processDefinitionVersion(processDefinition.getVersion())
                    .duration(timeConsumingStr)
                    .deploymentId(processDefinition.getDeploymentId())
                    .startUserId(startUserId)
                    .startUserName(user.getNickName())
                    .startTime(historicProcessInstance.getStartTime())
                    .build();
        }).collect(Collectors.toList());
        log.info("我的已办数据：{}", flowTaskDtos);

        vo.setList(flowTaskDtos);
    }

    /**
     * 时间戳转换成时间字符串
     *
     * @param timeConsuming 时间戳
     * @return {@link String } 时间字符串
     */
    private static String getTimeConsumingStr(long timeConsuming) {
        String timeConsumingStr = "";
        int days = (int) (timeConsuming / (60 * 60 * 24));
        int hours = (int) ((timeConsuming % (60 * 60 * 24)) / (60 * 60));
        int minutes = (int) ((timeConsuming % (60 * 60)) / 60);
        int seconds = (int) (timeConsuming % 60);

        if (days > 0) {
            timeConsumingStr += days + "天";
        }
        if (hours > 0) {
            timeConsumingStr += hours + "小时";
        }
        if (minutes > 0) {
            timeConsumingStr += minutes + "分钟";
        }
        if (seconds > 0) {
            // 当没有天、小时、分钟时，才显示秒
            if (timeConsumingStr.isEmpty()) {
                timeConsumingStr = seconds + "秒";
            } else {
                // 如果已经有天、小时或分钟了，则追加秒
                timeConsumingStr += seconds + "秒";
            }
        }

        // 处理全为0的情况，即时间消耗为0秒
        if (timeConsumingStr.isEmpty()) {
            timeConsumingStr = "0秒";
        }
        return timeConsumingStr;
    }

    /**
     * 查询已办流程图
     *
     * @param processId 流程实例 ID
     * @return {@link byte[] } 流程图
     */
    @Override
    public byte[] approvedFlowImg(String processId) {
        try {
            String processDefinitionId;
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processId).singleResult();
            if (Objects.isNull(processInstance)) {
                HistoricProcessInstance historicProcessInstance = historyService
                        .createHistoricProcessInstanceQuery()
                        .processInstanceId(processId)
                        .singleResult();
                processDefinitionId = historicProcessInstance.getProcessDefinitionId();

            }else {
                processDefinitionId = processInstance.getProcessDefinitionId();
            }
            String userName = WebUtils.loginUserName();
            // 获取活动节点
            List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processId)
                    .taskAssignee(userName)
                    .orderByHistoricActivityInstanceId()
                    .asc()
                    .list();
            String sequenceFlow = "sequenceFlow";
            List<String> highLightedNodes = historicActivityInstances.stream().map(historicActivityInstance -> {
                if (!sequenceFlow.equals(historicActivityInstance.getActivityType())) {
                    return historicActivityInstance.getActivityId();
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());

            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
            CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();
            ArrayList<String > arrayList = new ArrayList<>();
            InputStream inputStream = customProcessDiagramGenerator.generateDiagram(
                    bpmnModel,
                    "png",
                    highLightedNodes,
                    arrayList,
                    processEngineConfiguration.getActivityFontName(),
                    processEngineConfiguration.getLabelFontName(),
                    null,
                    1.0,
                    true
            );
            return IOUtils.toByteArray(inputStream);
        } catch (Exception e) {
                log.error("查询已办流程图 发生异常：", e);
            }
            return new byte[0];
        }
    }