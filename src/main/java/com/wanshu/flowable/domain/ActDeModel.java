package com.wanshu.flowable.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "act_de_model")
public class ActDeModel implements Serializable {
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "model_key")
    private String modelKey;

    @TableField(value = "description")
    private String description;

    @TableField(value = "model_comment")
    private String modelComment;

    @TableField(value = "created")
    private Date created;

    @TableField(value = "created_by")
    private String createdBy;

    @TableField(value = "last_updated")
    private Date lastUpdated;

    @TableField(value = "last_updated_by")
    private String lastUpdatedBy;

    @TableField(value = "version")
    private Integer version;

    @TableField(value = "model_editor_json")
    private String modelEditorJson;

    @TableField(value = "thumbnail")
    private byte[] thumbnail;

    @TableField(value = "model_type")
    private Integer modelType;

    @TableField(value = "tenant_id")
    private String tenantId;

    private static final long serialVersionUID = 1L;
}