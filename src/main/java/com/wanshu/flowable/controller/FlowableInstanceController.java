package com.wanshu.flowable.controller;

import com.wanshu.flowable.service.IFlowInstanceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 我的待办
 *
 * @author zengc
 * @date 2024/06/11
 */
@Controller
@RequestMapping("/flow/instance")
public class FlowableInstanceController {

    @Resource
    private IFlowInstanceService flowInstanceService;

    /**
     * 启动流程
     *
     * @param procDefId 流程定义key
     * @param variables 流程变量
     * @return {@link String} 流程实例列表页面
     */

    @PostMapping("/startProcessInstance")
    public String startProcessInstance(@RequestParam("processDefinitionKey") String procDefId,
                                       @RequestParam Map<String, Object> variables) {

        flowInstanceService.startProcessInstanceById(procDefId, variables);

        return "redirect:/flow/definition/latestList";
    }


}
