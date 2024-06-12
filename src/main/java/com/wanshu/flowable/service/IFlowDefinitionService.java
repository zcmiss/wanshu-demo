package com.wanshu.flowable.service;

import com.wanshu.flowable.domain.dto.FlowDeployProcDto;
import com.wanshu.flowable.domain.vo.FlowDeployProVo;
import liquibase.pro.packaged.S;

import java.io.InputStream;
import java.util.List;

/**
 * 流程定义的服务接口
 *
 * @author zengc
 * @date 2024/06/01
 */
public interface IFlowDefinitionService {
    /**
     * 分页查询
     *
     * @param pageVo 分页参数
     * @return {@link FlowDeployProVo } 分页结果
     */
    FlowDeployProVo listFlowDefinition(FlowDeployProVo pageVo);

    /**
     * 分页查询
     *
     * @param pageVo 分页参数
     * @return {@link FlowDeployProVo } 分页结果
     */
    FlowDeployProVo latestListFlowDefinition(FlowDeployProVo pageVo);

    /**
     * 更新流程状态
     *
     * @param definitionId 流程定义id
     */
    void updateState(String definitionId);

    /**
     * 删除流程
     *
     * @param deploymentId 流程部署id
     */
    void deleteFlow(String deploymentId);

    /**
     * 部署流程
     *
     * @param name        流程名称
     * @param category    流程分类
     * @param inputStream 流程文件
     */
    void deployFlow(String name, String category, InputStream inputStream);

    /**
     * 查询 流程图
     *
     * @param processDefinitionId 流程部署id
     * @return {@link byte[] } 流程图
     */
    byte[] flowImg(String processDefinitionId);

    /**
     * 查询流程图
     *
     * @param deploymentId 流程部署id
     * @return {@link String } 流程图
     */
    String flowXml(String deploymentId);
}
