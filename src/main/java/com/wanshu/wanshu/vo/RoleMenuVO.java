package com.wanshu.wanshu.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuVO {

    private Integer roleId;
    private List<Integer> menuIds;
}
