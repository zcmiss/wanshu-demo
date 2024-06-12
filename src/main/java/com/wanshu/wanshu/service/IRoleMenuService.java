package com.wanshu.wanshu.service;

import com.wanshu.wanshu.entity.RoleMenu;
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
public interface IRoleMenuService extends IService<RoleMenu> {

    List<RoleMenu> queryList(RoleMenu roleMenu);
}
