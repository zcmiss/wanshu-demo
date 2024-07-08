package com.wanshu.flowable.factory;

import com.wanshu.flowable.service.ActDeModelService;
import lombok.Data;
import org.flowable.engine.*;
import org.flowable.form.api.FormRepositoryService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 流程服务工厂
 * 常用的流程服务
 *
 * @author zengc
 * @date 2024/06/01
 */
@Component
@Data
public class FlowServiceFactory {
    /**
     * 流程引擎
     */
    @Resource
    protected ProcessEngine processEngine;
    /**
     * 流程存储服务
     */
    @Resource
    protected RepositoryService repositoryService;
    /**
     * 运行时服务
     */
    @Resource
    protected RuntimeService runtimeService;
    /**
     * 任务服务
     */
    @Resource
    protected TaskService taskService;
    /**
     * 身份服务
     */
    @Resource
    protected IdentityService identityService;
    /**
     * 动态流程服务
     */
    @Resource
    protected ManagementService managementService;
    /**
     * 历史服务
     */
    @Resource
    protected HistoryService historyService;
    /**
     * 访问表单数据和呈现的表单以启动新流程实例和完成任务
     */
    //@Resource
    //protected FormService formService;

    /**
     * 提供对表单存储库的访问的服务
     */
    @Resource
    protected FormRepositoryService formRepositoryService;

    @Resource
    protected ActDeModelService actDeModelService;

}
