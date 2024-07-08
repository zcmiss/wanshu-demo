package com.wanshu.flowable.service;

import com.wanshu.wanshu.utils.PageUtils;

public interface IFlowableFormService {
    /**
     *
     * @param pageUtils
     * @return {@link PageUtils }
     */
    PageUtils queryList(PageUtils pageUtils);
}
