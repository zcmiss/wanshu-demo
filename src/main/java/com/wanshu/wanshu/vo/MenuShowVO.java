package com.wanshu.wanshu.vo;

import com.wanshu.wanshu.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuShowVO extends Menu{

    private List<Menu> subMenus; // 对应的二级菜单
}
