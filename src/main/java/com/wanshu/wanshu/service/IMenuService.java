package com.wanshu.wanshu.service;

import com.wanshu.wanshu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wanshu.wanshu.vo.MenuShowVO;
import com.wanshu.wanshu.vo.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
public interface IMenuService extends IService<Menu> {

    MenuVO search(MenuVO vo);

    List<Menu> getAll1LevelMenus();

    Boolean deleteMenuById(Integer id);

    List<MenuShowVO> listByUserId(String username);
}
