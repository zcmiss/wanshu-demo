package com.wanshu.wanshu.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wanshu.wanshu.utils.Constant;
import com.wanshu.wanshu.utils.PageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserVO extends PageUtils {

    private String url = "/wanshu/user/list";

    public UserVO(List<?> list, int totalCount, int pageSize, int currPage) {
        super(list, totalCount, pageSize, currPage);
    }

    public UserVO(IPage<?> page) {
        super(page);
    }

    public UserVO(){

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
