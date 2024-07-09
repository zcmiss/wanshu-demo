package com.wanshu.flowable.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanshu.flowable.commom.ProcessConstant;
import com.wanshu.flowable.domain.ActDeModel;
import com.wanshu.flowable.domain.ActFoFormDefinition;
import com.wanshu.flowable.domain.FlowFrom;
import com.wanshu.flowable.factory.FlowServiceFactory;
import com.wanshu.flowable.service.ActDeModelService;
import com.wanshu.flowable.service.ActFoFormDefinitionService;
import com.wanshu.flowable.service.IFlowableFormService;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.form.api.FormDeployment;
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
    protected ActDeModelService actDeModelService;
    @Resource
    private IUserService iUserService;
    @Resource
    private ActFoFormDefinitionService actFoFormDefinitionService;

    /**
     * @param pageUtils
     * @return {@link PageUtils }
     */
    @Override
    public PageUtils queryList(PageUtils pageUtils) {
        Page<ActDeModel> actDeModelPage = actDeModelService.formList(pageUtils, 2);
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
            // 根据 formKey 去找act_fo_dorm_definition
            List<ActFoFormDefinition> actFoFormDefinitions = actFoFormDefinitionService.lambdaQuery()
                    .eq(StringUtils.isNotBlank(deModel.getModelKey()), ActFoFormDefinition::getKey, deModel.getModelKey())
                    .list();
            boolean isDeploy;
            if (actFoFormDefinitions.isEmpty()) {
                // 表示当前表单未部署
                isDeploy = false;
            } else {
                isDeploy = true ;
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
                    .isDeploy(isDeploy)
                    .build();
        }).collect(Collectors.toList());
        pageUtils.setTotalCount((int) actDeModelPage.getTotal());
        pageUtils.setList(flowFroms);
        return pageUtils;
    }

    /**
     * 完成表单部署
     *
     * @param formId 表单id
     */
    @Override
    public void deployForm(String formId) {
        // 根据formId 查询对于 表单的json文件
        ActDeModel deModel = actDeModelService.getById(formId);
        String modelEditorJson = deModel.getModelEditorJson();
        // 根据json信息来部署表单
        FormDeployment deployment = formRepositoryService.createDeployment()
                // 表单的资源名称后缀，必须是.form
                .addString(deModel.getModelKey() + ProcessConstant.FORM_FILE_SUFFIX, modelEditorJson)
                .name(deModel.getName())
                .deploy();
    }
}
