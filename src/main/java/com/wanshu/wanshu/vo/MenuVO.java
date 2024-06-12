package com.wanshu.wanshu.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanshu.wanshu.utils.PageUtils;

import java.util.List;

public class MenuVO extends PageUtils {
    private String url = "/wanshu/menu/list";

    public MenuVO(List<?> list, int totalCount, int pageSize, int currPage) {
        super(list, totalCount, pageSize, currPage);
    }

    public MenuVO(IPage<?> page) {
        super(page);
    }

    public MenuVO(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
