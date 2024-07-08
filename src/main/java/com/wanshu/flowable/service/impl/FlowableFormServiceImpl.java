package com.wanshu.flowable.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.flowable.domain.ActDeModel;
import com.wanshu.flowable.domain.FlowFrom;
import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.ActDeModelService;
import com.wanshu.flowable.service.IFlowableFormService;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表单的实现类
 *
 * @author zengc
 * @date 2024/07/08
 */
@Service
public class FlowableFormServiceImpl extends FlowServiceFactory implements IFlowableFormService {

    @Resource
    private IUserService iUserService;

    /**
     * @param pageUtils
     * @return {@link PageUtils }
     */
    @Override
    public PageUtils queryList(PageUtils pageUtils) {
        Page<ActDeModel> actDeModelPage = actDeModelService.formList(pageUtils,2);
        List<FlowFrom> flowFroms = actDeModelPage.getRecords().stream().map(deModel -> {
            String createdByName = null;
            String lastUpdatedByName = null;
            // 根据创建人获取对应的名称
            if (StringUtils.isNotBlank(deModel.getCreatedBy())) {
                User user = iUserService.getById(deModel.getCreatedBy());
                createdByName = user.getNickName();
            }
            if (StringUtils.isNotBlank(deModel.getLastUpdatedBy())) {
                User user = iUserService.getById(deModel.getLastUpdatedBy());
                lastUpdatedByName = user.getNickName();
            }
            return FlowFrom.builder()
                    .id(deModel.getId())
                    .name(deModel.getName())
                    .modelKey(deModel.getModelKey())
                    .description(deModel.getDescription())
                    .modelComment(deModel.getModelComment())
                    .createddatetime(deModel.getCreated())
                    .createdBy(deModel.getCreatedBy())
                    .lastUpdateddatetime(deModel.getLastUpdated())
                    .lastUpdateddatedBy(deModel.getLastUpdatedBy())
                    .version(deModel.getVersion())
                    .modelEditorJson(deModel.getModelEditorJson())
                    .modelType(deModel.getModelType())
                    .tenantId(deModel.getTenantId())
                    .createdByName(createdByName)
                    .lastUpdateddatedByName(lastUpdatedByName)
                    .build();
        }).collect(Collectors.toList());
        pageUtils.setTotalCount((int) actDeModelPage.getTotal());
        pageUtils.setList(flowFroms);
        return pageUtils;
    }
}
