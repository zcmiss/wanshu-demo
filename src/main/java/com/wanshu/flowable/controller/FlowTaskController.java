package com.wanshu.flowable.controller;

import com.wanshu.flowable.domain.vo.FlowTaskVo;
import com.wanshu.flowable.service.IFlowTaskService;
import com.wanshu.wanshu.utils.PageUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String myToDoTask(PageUtils pageVo, Model model) {
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

    /**
     *  任务审批
     *
     * @param taskVo
     * @return {@link String }
     */
    @PostMapping("/completeFlow")
    public String completeFlow(FlowTaskVo taskVo){
        boolean flag =  iflowTaskService.complete(taskVo);
        return "redirect:/flow/mytask/myToDoTask";
    }

    /**
     *  我的已办
     * @return
     */
    @GetMapping("finished")
    public String getFinishedList(PageUtils vo, Model model){
        iflowTaskService.finishedList(vo);
        model.addAttribute(SYS_PATH_URL, vo);
        return "/flow/myfinished/finished";
    }

    @GetMapping("/approvedFlowImg")
    @ResponseBody
    public byte[] approvedFlowImg(@RequestParam("processId") String processId){
        return iflowTaskService.approvedFlowImg(processId);
    }
}
