package com.wanshu.wanshu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@TableName("sys_oplog")
public class Oplog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作用户编号
     */
    private Integer opUserId;

    /**
     * 操作用户昵称
     */
    private String opNickName;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 数据id1
     */
    private Integer dataId1;

    /**
     * 数据id2
     */
    private Integer dataId2;

    /**
     * 操作的内容
     */
    private String opContent;

    /**
     * 业务模块
     */
    private String business;

    /**
     * 对应的功能
     */
    private String func;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }
    public String getOpNickName() {
        return opNickName;
    }

    public void setOpNickName(String opNickName) {
        this.opNickName = opNickName;
    }
    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
    public Integer getDataId1() {
        return dataId1;
    }

    public void setDataId1(Integer dataId1) {
        this.dataId1 = dataId1;
    }
    public Integer getDataId2() {
        return dataId2;
    }

    public void setDataId2(Integer dataId2) {
        this.dataId2 = dataId2;
    }
    public String getOpContent() {
        return opContent;
    }

    public void setOpContent(String opContent) {
        this.opContent = opContent;
    }
    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }
    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    @Override
    public String toString() {
        return "Oplog{" +
            "id=" + id +
            ", opUserId=" + opUserId +
            ", opNickName=" + opNickName +
            ", opTime=" + opTime +
            ", dataId1=" + dataId1 +
            ", dataId2=" + dataId2 +
            ", opContent=" + opContent +
            ", business=" + business +
            ", func=" + func +
        "}";
    }
}
