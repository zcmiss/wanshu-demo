package com.wanshu.wanshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.wanshu.entity.User;
import com.wanshu.wanshu.utils.PageUtils;
import com.wanshu.wanshu.vo.UserRoleVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
public interface IUserService extends IService<User> {

    List<User> queryByUser(User user);

    List<User> searchUser(String key);

    public PageUtils searchPageUser(PageUtils vo);

    Boolean checkUserName(String userName);

    Boolean checkMobile(String mobile);

    void allocateRole(UserRoleVO vo);

    List<Integer> getRoleByUserId(Integer id);

    User queryByUserName(String userName);

    List<User> queryByRoleId(String groupId);
}
