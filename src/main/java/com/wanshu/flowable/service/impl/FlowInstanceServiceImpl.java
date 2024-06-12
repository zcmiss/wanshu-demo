package com.wanshu.flowable.service.impl;

import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.IFlowInstanceService;
import com.wanshu.wanshu.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 我的待办 服务实现
 *
 * @author zengc
 * @date 2024/06/11
 */
@Slf4j
@Service
public class FlowInstanceServiceImpl extends FlowServiceFactory implements IFlowInstanceService {
    /**
     * 启动流程
     *
     * @param procDefId 流程定义key
     * @param variables 流程变量
     */
    @Override
    public void startProcessInstanceById(String procDefId, Map<String, Object> variables) {
        try {
            String userName = WebUtils.loginUserName();
            // 绑定当前认证的账号  我们的认证系统如果使用的是 SpringSecurity的话，默认就会设置当前登录的账号
            //  identityService.setAuthenticatedUserId(userName);
            // 我们把当前认证的账号 绑定在流程变量中
            //variables.put("loginUser",userName);
            //启动了一个流程实例

            variables.put("assigne1", "zhang");
            variables.put("assigne2", "wangwu");
            runtimeService.startProcessInstanceById(procDefId, variables);
            log.info("启动流程成功，流程定义key:{}", procDefId);
        } catch (Exception e) {
            log.error("启动流程失败，流程定义key:{}", procDefId, e);
            throw new RuntimeException("启动流程失败");
        }
    }
}
