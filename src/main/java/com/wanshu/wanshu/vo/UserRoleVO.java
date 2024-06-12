package com.wanshu.wanshu.vo;

import lombok.Data;

import java.util.List;

/**
 * 用户和角色的VO对象
 */
@Data
public class UserRoleVO {

    private Integer userId;

    private List<Integer> roleIds;
}
