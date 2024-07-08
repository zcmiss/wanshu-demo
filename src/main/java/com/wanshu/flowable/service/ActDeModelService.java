package com.wanshu.flowable.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.flowable.domain.ActDeModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.wanshu.utils.PageUtils;

public interface ActDeModelService extends IService<ActDeModel>{
    /**
     *
     * @param vo
     * @param type
     * @return {@link PageUtils }
     */
    Page<ActDeModel> formList(PageUtils vo, Integer type);
}
