package com.wanshu.flowable.domain.vo;

import lombok.Data;

/**
 * @author zengc
 * @date 2024/06/30
 */
@Data
public class FlowTaskVo {
    /**
     * 任务ID
     */
    private String taskId;
    /**
     * true：审核通过 false：审核拒绝
     */
    private Boolean approvd;
    /**
     * 审核意见
     */
    private String comment;

}
