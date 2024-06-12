package com.wanshu.wanshu;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.IdentityService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zengc
 * @date 2024/05/25
 */
@Slf4j
@SpringBootTest
public class AskForLeaveFlowableTest {

    ///**
    // * 动态流程服务
    // */
    //@Resource
    //private DynamicBpmnService dynamicBpmnService;
    //
    ///**
    // * 管理服务
    // */
    //@Resource
    //private ManagementService managementService;
    ///**
    // * 表单服务
    // */
    //@Resource
    //private FormService formService;

    ///**
    // * 历史服务
    // */
    //@Resource
    //private HistoryService historyService;
    ///**
    // * 流程引擎
    // */
    //@Resource
    //private ProcessEngine processEngine;
    /**
     * 任务服务
     */
    @Resource
    private TaskService taskService;

    /**
     * 用户服务
     */
    @Resource
    private IdentityService identityService;

    /**
     * 运行时服务
     */
    @Resource
    private RuntimeService runtimeService;

    /**
     * 流程定义和部署相关的service
     */
    @Resource
    private RepositoryService repositoryService;

    /**
     * 部署流程
     */
    @Test
    public void deploy() {
        // 部署流程
        Deployment deploy = repositoryService.createDeployment()
                // 添加流程文件
                .addClasspathResource("flowable-xml/leaveProcess.bpmn20.xml")
                // 设置流程名称
                .name("请假流程")
                // 设置流程分类
                .category("demo")
                // 设置租户id
                .tenantId("1")
                // 部署
                .deploy();
        log.info("-----------------部署相关的信息-----------------");
        log.info("部署成功，流程id:{}", deploy.getId());
        log.info("部署成功，流程名称:{}", deploy.getName());
        log.info("部署成功，流程分类:{}", deploy.getCategory());
        log.info("部署成功，流程时间:{}", deploy.getDeploymentTime());
        log.info("部署成功，流程key:{}", deploy.getKey());
        log.info("部署成功，流程租户id:{}", deploy.getTenantId());

        // 获取流程定义信息
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        log.info("-----------------流程定义相关的信息-----------------");
        log.info("流程定义id:{}", processDefinition.getId());
        log.info("流程定义名称:{}", processDefinition.getName());
        log.info("流程定义文件名称:{}", processDefinition.getResourceName());
        log.info("流程定义描述:{}", processDefinition.getDescription());
        log.info("流程定义文件信息 :{}", processDefinition.getDiagramResourceName());
        log.info("流程定义部署id:{}", processDefinition.getDeploymentId());
        log.info("流程定义版本:{}", processDefinition.getVersion());
    }

    /**
     * 启动流程
     */
    @Test
    public void startTest() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "zhangsan");
        variables.put("nrOfHolidays", "3天");
        variables.put("description", "回家探亲");
        // 设置当前处理用户
        identityService.setAuthenticatedUserId("zhangsan");
        // 流程定义ID
        String processDefinitionId = "holidayRequest:2:71ad447a-1ab8-11ef-bf58-00ff05ea07b9";
        // 启动流程 实例
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
        log.info("流程实例id:{}", processInstance.getId());
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTest() {
        // 设置当前处理用户
        identityService.setAuthenticatedUserId("lisi");
        // 流程实例id
        String processInstanceId = "902b34c8-1ab8-11ef-8720-00ff05ea07b9";
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved", false);
        // 查询任务
        Task task = taskService.createTaskQuery()
                // 激活的任务
                .active()
                .processInstanceId(processInstanceId)
                .singleResult();
        // 完成任务
        taskService.complete(task.getId(), variables,true);
    }

    /**
     * 查询任务是否结束
     */
    @Test
    public void endTest() {
        // 流程实例id
        String processInstanceId = "902b34c8-1ab8-11ef-8720-00ff05ea07b9";
        // 查询任务
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        log.info("任务是否结束:{}", task == null);
    }

}
