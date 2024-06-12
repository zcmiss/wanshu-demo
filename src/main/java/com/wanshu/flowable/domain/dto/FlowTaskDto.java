package com.wanshu.flowable.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 任务dto
 *
 * @author zengc
 * @date 2024/06/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowTaskDto {
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务执行编号
     */
    private String taskExecutionId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务定义key
     */
    private String taskDefinitionKey;
    /**
     * 任务执行人Id
     */
    private Long assigneeId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 流程发起人部门名称
     */
    private String startDeptName;
    /**
     * 任务执行人名称
     */
    private String assigneeName;
    /**
     * 流程发起人Id
     */
    private String startUserId;
    /**
     * 流程发起人名称
     */
    private String startUserName;
    /**
     * 流程类型
     */
    private String category;
    /**
     * 流程变量信息
     */
    private Object variables;
    /**
     * 局部变量信息
     */
    private Object taskLocalVariables;
    /**
     * 流程部署编号
     */
    private String deploymentId;
    /**
     * 流程Id
     */
    private String processDefinitionId;
    /**
     * 流程定义key
     */
    private String processDefinitionKey;
    /**
     * 流程定义名称
     */
    private String processDefinitionName;
    /**
     * 流程定义版本
     */
    private Integer processDefinitionVersion;
    /**
     * 流程实例Id
     */
    private String processInstanceId;
    /**
     * 历史流程实例Id
     */
    private String historicProcessInstanceId;
    /**
     * 任务耗时
     */
    private String duration;
    /**
     * 任务意见
     */
    private FlowConnectDto comment;
    /**
     * 候选执行人
     */
    private String candidateUsers;
    /**
     * 任务发起时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 任务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 任务完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;


}
