package com.wanshu.flowable.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 流程定义后保存相关的信息对象
 *
 * @author zengc
 * @date 2024/06/01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowDeployProcDto {
    /**
     * 流程定义id
     */
    private String id;
    /**
     * 流程定义名称
     */
    private String name;
    /**
     * 流程定义key
     */
    private String flowKey;
    /**
     * 流程部署分类
     */
    private String category;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 流程部署id
     */
    private String deploymentId;
    /**
     * 流程部署时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deploymentDate;
    /**
     * 是否挂起 1:正常 2:挂起
     */
    private Integer suspensionState;
    /**
     * 流程定义描述
     */
    private String description;
    /**
     * 流程定义版本
     */
    private Integer version;
    /**
     * 表单名称
     */
    private String formName;
    /**
     * 表单key
     */
    private String formKey;

}
