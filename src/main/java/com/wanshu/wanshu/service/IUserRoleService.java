package com.wanshu.wanshu.service;

import com.wanshu.wanshu.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
public interface IUserRoleService extends IService<UserRole> {

    List<UserRole> queryList(UserRole userRole);
}
