package com.wanshu.flowable.service;

import com.wanshu.flowable.domain.vo.FlowMyToDoTaskVo;

/**
 * @author zengc
 * @date 2024/06/11
 */
public interface IFlowTaskService {
    /**
     * 我的待办
     *
     * @param pageVo 我的待办
     */
    void  myToDoTaskList(FlowMyToDoTaskVo pageVo);
}
