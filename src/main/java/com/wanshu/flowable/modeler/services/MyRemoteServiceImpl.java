package com.wanshu.flowable.modeler.services;

import com.wanshu.wanshu.entity.Role;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.entity.UserRole;
import com.wanshu.wanshu.service.IRoleService;
import com.wanshu.wanshu.service.IUserRoleService;
import com.wanshu.wanshu.service.IUserService;
import com.wanshu.wanshu.service.impl.UserServiceImpl;
import org.flowable.ui.common.model.RemoteGroup;
import org.flowable.ui.common.model.RemoteToken;
import org.flowable.ui.common.model.RemoteUser;
import org.flowable.ui.common.service.idm.RemoteIdmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyRemoteServiceImpl implements RemoteIdmService {

    private Logger LOGGER = LoggerFactory.getLogger(MyRemoteServiceImpl.class);

    @Resource
    private IRoleService iRoleService;
    @Resource
    private IUserService iUserService;
    @Resource
    private IUserRoleService iUserRoleService;


    @Override
    public RemoteUser authenticateUser(String username, String password) {
        LOGGER.warn("MyRemoteServiceImpl:authenticateUser");
        return null;
    }

    @Override
    public RemoteToken getToken(String tokenValue) {
        LOGGER.warn("MyRemoteServiceImpl:getToken");
        return null;
    }

    /**
     * 根据用户角查询用户角色
     *
     * @param userId
     * @return {@link RemoteUser }
     */
    @Override
    public RemoteUser getUser(String userId) {
        LOGGER.warn("MyRemoteServiceImpl:getUser");
        User user = iUserService.queryByUserName(userId);
        RemoteUser remoteUser = new RemoteUser();
        remoteUser.setId(user.getUserName());
        remoteUser.setFirstName(user.getNickName());
        remoteUser.setFullName(user.getNickName());
        // 根据当前用户查询当前角色
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        // 用户和角色的关系
        List<UserRole> userRoleList = iUserRoleService.queryList(userRole);
        List<RemoteGroup> remoteGroups = userRoleList.stream().map(userRole1 -> {
            RemoteGroup remoteGroup = new RemoteGroup();
            Role role = iRoleService.getById(userRole1.getRoleId());
            remoteGroup.setId(role.getId().toString());
            remoteGroup.setName(role.getRoleName());
            return remoteGroup;
        }).collect(Collectors.toList());
        remoteUser.setGroups(remoteGroups);
        return remoteUser;
    }

    /**
     * 分配用户或者候选组
     *
     * @param filter
     * @return {@link List }<{@link RemoteUser }>
     */
    @Override
    public List<RemoteUser> findUsersByNameFilter(String filter) {
        LOGGER.warn("MyRemoteServiceImpl:findUsersByNameFilter");
        List<User> users = iUserService.searchUser(filter);
        // 根据查询的条件查收出相关的用户信息
        return users.stream().map(user -> {
            RemoteUser remoteUser = new RemoteUser();
            remoteUser.setId(user.getUserName());
            remoteUser.setFirstName(user.getNickName());
            return remoteUser;
        }).collect(Collectors.toList());
    }

    /**
     * 根据角色编号查询所有用户
     *
     * @param groupId
     * @return {@link List }<{@link RemoteUser }>
     */
    @Override
    public List<RemoteUser> findUsersByGroup(String groupId) {
        LOGGER.warn("MyRemoteServiceImpl:findUsersByGroup");
        List<User> users =  iUserService.queryByRoleId(groupId);
        return users.stream().map(user -> {
            RemoteUser remoteUser = new RemoteUser();
            remoteUser.setId(user.getUserName());
            remoteUser.setFirstName(user.getNickName());
            return remoteUser;
        }).collect(Collectors.toList());
    }

    /**
     *
     * org.flowable.ui.modeler.rest.app.EditorGroupsResource#getGroups(java.lang.String)
     * url: http://localhost:8081/flowable-modeler-demo/app/rest/editor-groups
     *
     * @param groupId
     * @return
     */
    @Override
    public RemoteGroup getGroup(String groupId) {
        LOGGER.warn("MyRemoteServiceImpl:getGroup");
        Role role = iRoleService.getById(groupId);
        RemoteGroup remoteGroup = new RemoteGroup();
        remoteGroup.setId(role.getId().toString());
        remoteGroup.setName(role.getRoleName());
        return remoteGroup;
    }

    /**
     * 分配用户功能
     * <p>
     * http://localhost:8081/flowable-modeler-demo/app/rest/editor-groups
     * org.flowable.ui.modeler.rest.app.EditorGroupsResource#getGroups(java.lang.String)
     *
     * @param filter
     * @return
     */
    @Override
    public List<RemoteGroup> findGroupsByNameFilter(String filter) {
        LOGGER.warn("MyRemoteServiceImpl:findGroupsByNameFilter");
        // 根据查询的条件查收出相关的角色信息
        List<Role> roles = iRoleService.findByRoleName(filter);
        return roles.stream().map(role -> {
            RemoteGroup remoteGroup = new RemoteGroup();
            remoteGroup.setId(role.getId().toString());
            remoteGroup.setName(role.getRoleName());
            return remoteGroup;
        }).collect(Collectors.toList());
    }
}
