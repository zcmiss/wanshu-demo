package com.wanshu.flowable.controller;

import com.wanshu.flowable.service.IFlowDefinitionService;
import com.wanshu.wanshu.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

import static com.wanshu.wanshu.common.SysConstant.SYS_PATH_URL;

/**
 * 流程定义控制器
 * 流程定义查询
 * 流程部署
 * 删除流程
 * 挂起/激活流程
 * 查看流程图
 *
 * @author zengc
 * @date 2024/06/01
 */
@Controller
@RequestMapping("flow/definition")
@Slf4j
public class FlowDefinitionController {
    /**
     * 流程定义服务
     */
    @Resource
    private IFlowDefinitionService flowDefinitionService;

    /**
     * 查询流程定义
     *
     * @param pageVo 分页参数
     * @param mode   模型
     * @return {@link String } 流程定义列表页面
     */
    @GetMapping("/list")
    public String listFlowDefinition(PageUtils pageVo, org.springframework.ui.Model mode) {
        pageVo = flowDefinitionService.listFlowDefinition(pageVo);
        mode.addAttribute(SYS_PATH_URL, pageVo);
        return "flow/definition/flowDefinition";
    }

    /**
     * 查询流程定义
     *
     * @param pageVo 分页参数
     * @param mode   模型
     * @return {@link String } 流程定义列表页面
     */
    @GetMapping("/latestList")
    public String latestListFlowDefinition(PageUtils pageVo, org.springframework.ui.Model mode) {
        pageVo = flowDefinitionService.latestListFlowDefinition(pageVo);
        mode.addAttribute(SYS_PATH_URL, pageVo);
        return "flow/process/startFlow";
    }

    /**
     * 部署流程
     *
     * @param name     流程名称
     * @param category 流程分类
     * @param file     流程文件
     * @return {@link String }
     */
    @PostMapping("/deployFlow")
    public String deployFlow(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "category", required = false) String category,
                             @RequestPart("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            flowDefinitionService.deployFlow(name, category, inputStream);
        } catch (IOException e) {
            log.error("部署流程失败", e);
        }
        return "redirect:/flow/definition/list";
    }

    /**
     * 更新流程状态
     *
     * @param definitionId 流程定义id
     * @return {@link String } 重定向到流程定义列表
     */
    @GetMapping("/updateState")
    public String updateFlowDefinition(@RequestParam("definitionId") String definitionId) {
        flowDefinitionService.updateState(definitionId);
        return "redirect:/flow/definition/list";
    }

    /**
     * 删除流程 通过部署id
     *
     * @param deploymentId 流程部署id
     * @return {@link String } 重定向到流程定义列表
     */
    @GetMapping("deleteFlow/{deploymentId}")
    public String deleteFlow(@PathVariable("deploymentId") String deploymentId) {
        flowDefinitionService.deleteFlow(deploymentId);
        return "redirect:/flow/definition/list";
    }

    /**
     * 查看流程图
     *
     * @param processDefinitionId 流程部署id
     * @return {@link String } 流程图 img
     */
    @GetMapping(value = "/flowImg", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] flowImg(@RequestParam("processDefinitionId") String processDefinitionId) {
        return flowDefinitionService.flowImg(processDefinitionId);
    }

    /**
     * 查看流程图
     *
     * @param processDefinitionId 流程部署id
     * @return {@link String } 流程图xml
     */
    @GetMapping(value = "/flowXml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String flowXml(@RequestParam("processDefinitionId") String processDefinitionId) {
        return flowDefinitionService.flowXml(processDefinitionId);
    }

}
