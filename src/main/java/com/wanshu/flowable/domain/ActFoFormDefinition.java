package com.wanshu.flowable.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "act_fo_form_definition")
public class ActFoFormDefinition implements Serializable {
    @TableId(value = "ID_", type = IdType.INPUT)
    private String id;

    @TableField(value = "NAME_")
    private String name;

    @TableField(value = "VERSION_")
    private Integer version;

    @TableField(value = "KEY_")
    private String key;

    @TableField(value = "CATEGORY_")
    private String category;

    @TableField(value = "DEPLOYMENT_ID_")
    private String deploymentId;

    @TableField(value = "TENANT_ID_")
    private String tenantId;

    @TableField(value = "RESOURCE_NAME_")
    private String resourceName;

    @TableField(value = "DESCRIPTION_")
    private String description;

    private static final long serialVersionUID = 1L;
}