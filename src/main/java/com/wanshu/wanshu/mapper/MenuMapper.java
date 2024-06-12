package com.wanshu.wanshu.mapper;

import com.wanshu.wanshu.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wanshu
 * @since 2022-06-20
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> selectByUserName(@Param("username") String username);
}
