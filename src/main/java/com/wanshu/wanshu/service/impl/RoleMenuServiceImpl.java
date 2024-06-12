package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanshu.wanshu.entity.RoleMenu;
import com.wanshu.wanshu.mapper.RoleMenuMapper;
import com.wanshu.wanshu.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<RoleMenu> queryList(RoleMenu roleMenu) {
        QueryWrapper queryWrapper = new QueryWrapper<RoleMenu>();
        queryWrapper.eq(roleMenu.getMenuId() != null,"menu_id",roleMenu.getMenuId())
                .eq(roleMenu.getRoleId()!=null ,"role_id",roleMenu.getRoleId());
        return this.list(queryWrapper);
    }
}
