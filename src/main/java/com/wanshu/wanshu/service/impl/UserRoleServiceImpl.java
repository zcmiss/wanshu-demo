package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.mapper.UserRoleMapper;
import com.wanshu.wanshu.service.IUserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public List<UserRole> queryList(UserRole userRole) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(userRole.getUserId()!=null,"user_id",userRole.getUserId())
                .eq(userRole.getRoleId() != null,"role_id",userRole.getRoleId());
        return this.baseMapper.selectList(queryWrapper);
    }
}
