package com.wanshu.flowable.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 表单实体
 *
 * @author zengc
 * @date 2024/07/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlowFrom {
    private String id;
    private String name;
    private String modelKey;
    private String description;
    private String modelComment;
    private String key;
    private Date createddatetime;
    private String createdBy;
    private String createdByName;
    private Date lastUpdateddatetime;
    private String lastUpdateddatedBy;
    private String lastUpdateddatedByName;
    private Integer version;
    private String modelEditorJson;
    /**
     * 是否已部署
     */
    private Boolean isDeploy;
    /**
     * 0:流程图;2:表单；3：应用
     */
    private Integer modelType;
    private String tenantId;


}
