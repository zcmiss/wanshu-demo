package com.wanshu.flowable.domain.vo;

import com.wanshu.wanshu.utils.PageUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 我的待办
 *
 * @author zengc
 * @date 2024/06/11
 */
@Getter
@Setter
public class FlowProcLatestVo extends PageUtils {
    String url = "/flow/definition/latestList";
}
