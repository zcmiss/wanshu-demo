package com.wanshu.flowable.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 我的待办
 * 任务意见
 *
 * @author zengc
 * @date 2024/06/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowConnectDto {
    /**
     * 意见类型 0:正常意见 1:退回意见 2：驳回意见
     */
    private Integer type;
    /**
     * 意见内容
     */
    private String content;
}
