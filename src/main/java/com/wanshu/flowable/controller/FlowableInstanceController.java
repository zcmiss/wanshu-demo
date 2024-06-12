package com.wanshu.flowable.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 我的待办
 *
 * @author zengc
 * @date 2024/06/11
 */
@Controller
@RequestMapping("/flow/instance")
public class FlowableInstanceController {
    /**
     * 启动流程
     *
     * @param processDefinitionKey 流程定义key
     * @param variables             流程变量
     * @return  {@link String} 流程实例列表页面
     */

    @PostMapping("/startProcessInstance")
    public String startProcessInstance(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                       @RequestParam("variables") String variables) {
        return "redirect:/flow/instance/list";
    }


}
