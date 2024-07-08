package com.wanshu.flowable.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.flowable.domain.FlowFrom;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.PageUtils;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.flowable.mapper.ActDeModelMapper;
import com.wanshu.flowable.domain.ActDeModel;
import com.wanshu.flowable.service.ActDeModelService;

import javax.annotation.Resource;

/**
 * 表单
 *
 * @author zengc
 * @date 2024/07/08
 */
@Service
public class ActDeModelServiceImpl extends ServiceImpl<ActDeModelMapper, ActDeModel> implements ActDeModelService {

    @Override
    public Page<ActDeModel> formList(PageUtils vo, Integer type) {
        int currPage = vo.getCurrPage();
        int pageSize = vo.getPageSize();
        Page<ActDeModel> page = lambdaQuery()
                .eq(Objects.nonNull(type), ActDeModel::getModelType, type)
                .and(StringUtils.isNotBlank(vo.getKey()), i -> i.like(ActDeModel::getName, vo.getKey())
                        .or()
                        .like(ActDeModel::getDescription, vo.getKey())
                )
                .page(new Page<>(currPage, pageSize));
        return page;
    }
}
