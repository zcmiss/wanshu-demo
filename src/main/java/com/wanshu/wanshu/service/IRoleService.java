package com.wanshu.wanshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.vo.MenuShowVO;
import com.wanshu.wanshu.vo.RoleMenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
public interface IRoleService extends IService<Role> {

    PageUtils search(PageUtils vo);

    Boolean canIDeleteById(Integer id);

    List<Integer> queryMenuByRoleId(Integer roleId);

    List<MenuShowVO> getMenuShow();

    void allocateMenu(RoleMenuVO vo);

    List<Role> findByRoleName(String filter);
}
