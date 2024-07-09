package com.wanshu.flowable.controller;

import com.wanshu.flowable.service.IFlowableFormService;
import com.wanshu.wanshu.utils.PageUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

import static com.wanshu.wanshu.common.SysConstant.SYS_PATH_URL;

/**
 * 表单
 *
 * @author zengc
 * @date 2024/07/08
 */
@RequestMapping("/flow/form")
@Controller
public class FlowFornConteoller {

    @Resource
    IFlowableFormService iFlowableFormService;

    @GetMapping("/formList")
    public String formList(PageUtils pageUtils, Model model){
        PageUtils pageUtils1 = iFlowableFormService.queryList(pageUtils);
        model.addAttribute(SYS_PATH_URL, pageUtils);
        return "flow/form/formManager";
    }

    /**
     * 完成表单的部署操作
     * @param formId 表单Id
     * @return
     */
    @GetMapping("/deployForm")
    public String  deployForm(@RequestParam(value = "formId") String formId){
        iFlowableFormService.deployForm(formId);
        return "redirect:/flow/form/formList";
    }

}
