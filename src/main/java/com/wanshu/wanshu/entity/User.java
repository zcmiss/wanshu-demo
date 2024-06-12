package com.wanshu.wanshu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wanshu.wanshu.annotation.UserNameUniqueValid;
import com.wanshu.wanshu.exception.group.SaveGroupInterface;
import com.wanshu.wanshu.exception.group.UpdateGroupInterface;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@TableName("sys_user")
public class User  implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     * 更新的时候ID不能为空
     * 添加的时候ID为空
     */
    @NotNull(groups = UpdateGroupInterface.class,message = "更新数据ID不能为空")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     * 自定义校验注解
     * 1.编写一个自定义的校验注解
     * 2.编写一个自定义的校验器  校验数据是否合法
     * 3.自定义的校验注解需要关联自定义的校验器
     */
    @NotBlank(groups = {SaveGroupInterface.class},
            message = "账号不能为空")
    //@Length(max = 20,min = 3,message = "账号的长度3~30")
    @Pattern(regexp = "^[a-zA-Z][1-9a-zA-Z]{1,19}$",
            groups = {SaveGroupInterface.class},
            message = "账号是数字加字母组成，字母开头,长度3~20位")
    // 自定义校验注解使用
    @UserNameUniqueValid(message = "账号重复，请重新输入",groups = SaveGroupInterface.class)
    private String userName;

    /**
     * 密码
     */
    @NotBlank(groups = {UpdateGroupInterface.class},
            message = "密码不能为空")
    @Length(min = 3,max = 10,message = "密码的长度3~10")
    private String password;

    /**
     * 昵称
     */
    @NotBlank(groups = {SaveGroupInterface.class,UpdateGroupInterface.class},
            message = "昵称不能为空")
    private String nickName;

    /**
     * 手机号
     */
    @NotBlank(groups = {SaveGroupInterface.class,UpdateGroupInterface.class},
            message = "手机号不能为空")
    //@Length(min = 11,max = 11,message = "手机号的长度必须是11位")
    @Pattern(regexp = "^1[3-9][1-9]{9}$",message = "手机号11位数字")
    private String mobile;

    /**
     * 排序字段
     */
    @NotNull(message = "排序号不能为空")
    @Min(value = 0,
            groups = {SaveGroupInterface.class,UpdateGroupInterface.class},
            message = "排序不能小于0")

    private Integer orderRank;

    /**
     * 是否删除 0正常 1删除
     */
    @TableLogic(delval = "1",value = "0")
    private Integer isDeleted;

    /**
     * 绑定的对应的员工编号
     */
    private Integer bindId;

    /**
     * 账号类型  1 系统账号 2 员工 3 候选人 ...
     */
    private Integer accountType;

    /**
     * 账号状态 0 可用  1冻结
     */
    private Integer accountState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getOrderRank() {
        return orderRank;
    }

    public void setOrderRank(Integer orderRank) {
        this.orderRank = orderRank;
    }
    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Integer getBindId() {
        return bindId;
    }

    public void setBindId(Integer bindId) {
        this.bindId = bindId;
    }
    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
    public Integer getAccountState() {
        return accountState;
    }

    public void setAccountState(Integer accountState) {
        this.accountState = accountState;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userName=" + userName +
            ", password=" + password +
            ", nickName=" + nickName +
            ", mobile=" + mobile +
            ", orderRank=" + orderRank +
            ", isDeleted=" + isDeleted +
            ", bindId=" + bindId +
            ", accountType=" + accountType +
            ", accountState=" + accountState +
            ", createTime=" + createTime +
            ", updateTime=" + updateTime +
            ", createUser=" + createUser +
            ", updateUser=" + updateUser +
        "}";
    }
}
