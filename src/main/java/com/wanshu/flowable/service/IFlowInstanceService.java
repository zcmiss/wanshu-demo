package com.wanshu.flowable.service;

import java.util.Map;

/**
 * 我的待办 服务接口
 *
 * @author zengc
 * @date 2024/06/11
 */
public interface IFlowInstanceService {
    /**
     * 启动流程
     *
     * @param procDefId 流程定义key
     * @param variables 流程变量
     */
    void startProcessInstanceById(String procDefId, Map<String, Object> variables);
}
