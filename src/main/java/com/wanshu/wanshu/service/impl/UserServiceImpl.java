package com.wanshu.wanshu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.mapper.UserMapper;
import com.wanshu.wanshu.service.IUserRoleService;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.utils.Query;
import com.wanshu.wanshu.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    IUserRoleService userRoleService;


    @Override
    public List<User> queryByUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user != null) {
            // 根据账号查询相关信息
            queryWrapper.eq(StringUtils.isNotBlank(user.getUserName()), "user_name", user.getUserName());
        }
        return this.baseMapper.selectList(queryWrapper);
    }


    @Override
    public List<User> searchUser(String key) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(key), "user_name", key)
                .or().like(StringUtils.isNotBlank(key), "nick_name", key)
                .orderByAsc("order_rank");
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageUtils searchPageUser(PageUtils vo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(vo.getKey()), "user_name", vo.getKey())
                .or().like(StringUtils.isNotBlank(vo.getKey()), "nick_name", vo.getKey())
                .orderByAsc("order_rank");
        IPage<User> page = this.page(new Query<User>().getPage(vo.getMap()),
                queryWrapper);
        vo.setPage(page);
        return vo;
    }

    /**
     * 判断账号是否存在
     *
     * @param userName
     * @return
     */
    @Override
    public Boolean checkUserName(String userName) {
        Long count = this.getBaseMapper().selectCount(new QueryWrapper<User>().eq("user_name", userName));
        return count == 0 ? true : false;
    }

    @Override
    public Boolean checkMobile(String mobile) {
        Long count = this.getBaseMapper().selectCount(new QueryWrapper<User>().eq("mobile", mobile));
        return count == 0 ? true : false;
    }

    /**
     * 给用户分配对应的角色
     *
     * @param vo
     */
    @Transactional
    @Override
    public void allocateRole(UserRoleVO vo) {
        // 对应的用户
        Integer userId = vo.getUserId();
        // 先删除已经分配的角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        // 分配的角色
        List<Integer> roleIds = vo.getRoleIds();
        // 组合为操作的数据
        List<UserRole> list = roleIds.stream().map(item -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(item);
            return userRole;
        }).collect(Collectors.toList());
        // 批量保存
        userRoleService.saveBatch(list);
    }

    @Override
    public List<Integer> getRoleByUserId(Integer id) {
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        List<UserRole> userRoleList = userRoleService.queryList(userRole);
        if (userRoleList == null || userRoleList.size() == 0) {
            return null;
        }
        return userRoleList.stream().map(item -> {
            return item.getRoleId();
        }).collect(Collectors.toList());
    }

    /**
     * 根据账号查询用户信息
     *
     * @param userName 账号
     * @return 用户信息
     */
    @Override
    public User queryByUserName(String userName) {
        List<User> users = lambdaQuery()
                .eq(Objects.nonNull(userName), User::getUserName, userName)
                .list();
        return users.isEmpty() ? new User() : users.get(0);
    }

    /**
     * 根据角色编号查询所有用户
     *
     * @param groupId
     * @return {@link List }<{@link User }>
     */
    @Override
    public List<User> queryByRoleId(String groupId) {
        // 根据角色id 查询关系表
        List<UserRole> userRoles = userRoleService
                .lambdaQuery()
                .eq(StringUtils.isNotBlank(groupId), UserRole::getRoleId, groupId)
                .list();
        return Optional.of(userRoles.stream()
                        .map(UserRole::getUserId)
                        .collect(Collectors.toList()))
                .filter(ids -> !ids.isEmpty())
                .map(ids -> lambdaQuery().in(User::getId, ids).list())
                .orElseGet(ArrayList::new);
    }
}
