package com.wanshu.flowable.service.impl;

import com.wanshu.flowable.domain.dto.FlowTaskDto;
import com.wanshu.flowable.domain.vo.FlowMyToDoTaskVo;
import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.IFlowTaskService;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.WebUtils;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zengc
 * @description 我的待办 服务实现
 * @date 2024/06/11
 */
@Service
public class FlowTaskServiceImpl extends FlowServiceFactory implements IFlowTaskService {

    private static final Logger log = LoggerFactory.getLogger(FlowTaskServiceImpl.class);
    @Resource
    IUserService userService;

    /**
     * 我的待办
     *
     * @param pageVo 我的待办
     */
    @Override
    public void myToDoTaskList(FlowMyToDoTaskVo pageVo) {
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
        pageVo.setTotalPage(count);
        pageVo.setTotalPage((count + 1) / pageSize + 1);

        if (tasks.isEmpty()) {
            // TODO: 2024/06/11 无数据处理
        }

        List<FlowTaskDto> flowTaskDtos = tasks.stream().map(task -> {
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
            User user = userService.queryByUserName(userName);
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
}

