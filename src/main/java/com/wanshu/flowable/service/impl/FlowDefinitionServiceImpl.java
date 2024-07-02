package com.wanshu.flowable.service.impl;

import com.wanshu.flowable.domain.dto.FlowDeployProcDto;
import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.IFlowDefinitionService;
import com.wanshu.wanshu.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wanshu.flowable.commom.ProcessConstant.BPMN_FILE_SUFFIX;
import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * 流程定义相关的service 实现
 *
 * @author zengc
 * @date 2024/06/01
 */
@Slf4j
@Service
public class FlowDefinitionServiceImpl extends FlowServiceFactory implements IFlowDefinitionService {
    /**
     * 分页查询
     *
     * @param pageVo 分页参数
     * @return {@link PageUtils } 分页结果
     */
    @Override
    public PageUtils listFlowDefinition(PageUtils pageVo) {
        // 1： 查询所有的部署流程
        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();

        if (Objects.nonNull(pageVo.getKey())) {
            deploymentQuery.deploymentNameLike("%" + pageVo.getKey() + "%");
        }
        int pageSize = pageVo.getPageSize();
        int currPage = pageVo.getCurrPage();
        List<Deployment> deployments = deploymentQuery.listPage(pageSize * (currPage - 1), pageSize);
        // 如果没有部署流程信息 封装在FlowDeployProcDto中
        List<FlowDeployProcDto> flowDeployProcDtos = deployments.stream().map(deployment -> {
            // 根据部署的流程ID查询对应的 流程定义信息
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();
            // 封装流程定义信息
            return FlowDeployProcDto.builder()
                    .id(processDefinition.getId())
                    .name(processDefinition.getName())
                    .category(processDefinition.getCategory())
                    .tenantId(processDefinition.getTenantId())
                    .deploymentId(processDefinition.getDeploymentId())
                    .flowKey(processDefinition.getKey())
                    .deploymentDate(deployment.getDeploymentTime())
                    .version(Integer.valueOf(processDefinition.getVersion()))
                    .suspensionState(Integer.valueOf(processDefinition.isSuspended() ? 2 : 1))
                    .build();
        })
                .sorted((o1, o2) -> o2.getDeploymentDate().compareTo(o1.getDeploymentDate()))
                .collect(Collectors.toList());
        pageVo.setList(flowDeployProcDtos);
        int count = (int) deploymentQuery.count();
        pageVo.setTotalCount(count);
        pageVo.setTotalPage((count + 1) / pageVo.getPageSize() + 1);
        return pageVo;
    }

    /**
     * 分页查询
     *
     * @param pageVo 分页参数
     * @return {@link PageUtils } 分页结果
     */
    @Override
    public PageUtils latestListFlowDefinition(PageUtils pageVo) {
        // 1： 查询所有的部署流程
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();

        if (Objects.nonNull(pageVo.getKey())) {
            processDefinitionQuery.processDefinitionNameLike("%" + pageVo.getKey() + "%");
        }
        int pageSize = pageVo.getPageSize();
        int currPage = pageVo.getCurrPage();
        List<ProcessDefinition> processDefinitions = processDefinitionQuery
                // 查询最新版本的流程定义
                .latestVersion()
                .listPage(pageSize * (currPage - 1), pageSize);
        // 如果没有部署流程信息 封装在FlowDeployProcDto中
        List<FlowDeployProcDto> flowDeployProcDtos =
                processDefinitions.stream().map(processDefinition -> {
                    Deployment deployment = repositoryService
                            .createDeploymentQuery()
                            .deploymentId(processDefinition.getDeploymentId())
                            .singleResult();
                    // 封装流程定义信息
                    return FlowDeployProcDto.builder()
                            .id(processDefinition.getId())
                            .name(processDefinition.getName())
                            .category(processDefinition.getCategory())
                            .tenantId(processDefinition.getTenantId())
                            .deploymentId(processDefinition.getDeploymentId())
                            .flowKey(processDefinition.getKey())
                            .deploymentDate(deployment.getDeploymentTime())
                            .version(Integer.valueOf(processDefinition.getVersion()))
                            .suspensionState(Integer.valueOf(processDefinition.isSuspended() ? 2 : 1))
                            .build();
                })
                        .sorted(Comparator.comparing(FlowDeployProcDto::getVersion))
                        .collect(Collectors.toList());
        pageVo.setList(flowDeployProcDtos);
        int count = (int) processDefinitionQuery.count();
        pageVo.setTotalCount(count);
        pageVo.setTotalPage((count + 1) / pageVo.getPageSize() + 1);
        return pageVo;
    }

    /**
     * 部署流程
     *
     * @param name        流程名称
     * @param category    流程分类
     * @param inputStream 流程文件
     */
    @Override
    public void deployFlow(String name, String category, InputStream inputStream) {
        // 部署流程
        repositoryService.createDeployment()
                // 添加流程名称
                .name(name)
                // 添加流程分类
                .category(category)
                // 添加流程文件
                .addInputStream(name + BPMN_FILE_SUFFIX, inputStream)
                // 部署
                .deploy();
    }

    /**
     * 更新流程状态
     *
     * @param definitionId 流程定义id
     */
    @Override
    public void updateState(String definitionId) {
        // 根据流程定义id查询流程定义信息
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(definitionId)
                .singleResult();
        // 判断流程定义是否挂起
        boolean suspended = processDefinition.isSuspended();
        // 如果是挂起状态，激活流程定义；如果是激活状态，挂起流程定义
        if (suspended) {
            // 激活
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
        } else {
            // 挂起
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        }
    }

    /**
     * 删除流程
     *
     * @param deploymentId 流程部署id
     */
    @Override
    public void deleteFlow(String deploymentId) {
        // 删除流程部署信息 true 表示级联删除
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 查询 流程图
     *
     * @param processDefinitionId 流程部署id
     * @return {@link byte[] } 流程图
     */
    @Override
    public byte[] flowImg(String processDefinitionId) {
        try {
            // 获取流程图
            InputStream resourceAsStream = repositoryService
                    .getProcessDiagram(processDefinitionId);
            // 转换为byte数组
            return toByteArray(resourceAsStream);
        } catch (IOException e) {
            log.error("查询流程图失败：", e);
        }
        return new byte[0];
    }

    /**
     * 查询流程图
     *
     * @param processDefinitionId 流程部署id
     * @return {@link String } 流程图
     */
    @Override
    public String flowXml(String processDefinitionId) {
        try {
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId).singleResult();
            // 获取流程图
            InputStream resourceAsStream = repositoryService
                    .getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getResourceName());
            // 流转换成字符串
            return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("查询流程图失败：", e);
        }
        return "";
    }
}
