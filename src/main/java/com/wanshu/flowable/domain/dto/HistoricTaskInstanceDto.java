package com.wanshu.flowable.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.flowable.task.api.history.HistoricTaskInstance;

import java.util.Date;

/**
 * @author zengc
 * @date 2024/07/06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoricTaskInstanceDto {
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
     * 任务执行人名称
     */
    private String assigneeName;
    /**
     * 任务完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishTime;
    /**
     * 任务耗时
     */
    private String duration;
    /**
     * 审批意见
     */
    private String comment;
    /**
     * 审批状态
     */
    private String state;
}
