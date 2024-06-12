package com.wanshu.wanshu.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanshu.wanshu.utils.PageUtils;

import java.util.List;

public class RoleVO extends PageUtils {

    private String url = "/wanshu/role/list";

    public RoleVO(List<?> list, int totalCount, int pageSize, int currPage) {
        super(list, totalCount, pageSize, currPage);
    }

    public RoleVO(IPage<?> page) {
        super(page);
    }

    public RoleVO(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
