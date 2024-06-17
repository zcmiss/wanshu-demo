package com.wanshu.flowable.controller;

import com.wanshu.flowable.domain.vo.FlowMyToDoTaskVo;
import com.wanshu.flowable.service.IFlowTaskService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.wanshu.wanshu.common.SysConstant.SYS_PATH_URL;

/**
 * @author zengc
 * @date 2024/06/11
 */
@Controller
@RequestMapping("/flow/mytask")
public class FlowTaskController {
    @Resource
    IFlowTaskService iflowTaskService;

    /**
     * 我的待办
     *
     * @param pageVo 分页参数
     * @return {@link String } 我的待办列表页面
     */
    @GetMapping("/myToDoTask")
    public String myToDoTask(FlowMyToDoTaskVo pageVo, Model model) {
        iflowTaskService.myToDoTaskList(pageVo);
        model.addAttribute(SYS_PATH_URL, pageVo);
        return "flow/mytask/toDoTaskFlow";
    }


    /**
     * 查看流程图
     *
     * @param processId 流程部署id
     * @return {@link String } 流程图 img
     */
    @GetMapping(value = "/flowImg", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] flowImg(@RequestParam("processId") String processId) {
        return iflowTaskService.diagram(processId);
    }
}
