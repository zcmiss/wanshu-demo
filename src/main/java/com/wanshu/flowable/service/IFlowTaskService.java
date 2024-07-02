package com.wanshu.flowable.service;

import com.wanshu.flowable.domain.vo.FlowTaskVo;
import com.wanshu.wanshu.utils.PageUtils;

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
    void  myToDoTaskList(PageUtils pageVo);

    byte[] diagram(String processId);

    /**
     * 审批任务
     * @param taskVo 任务
     * @return {@link Boolean } true：审核通过 false：审核拒绝
     */
    boolean complete(FlowTaskVo taskVo);

    void finishedList(PageUtils vo);

    byte[] approvedFlowImg(String processId);
}
