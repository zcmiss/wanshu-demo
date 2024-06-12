package com.wanshu.flowable.delage;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * @author zengc
 * @date 2024/05/26
 */
@Slf4j
public class SendRejectionMail implements JavaDelegate {
    /**
     * @param delegateExecution 执行实例
     */
    @Override
    public void execute(DelegateExecution delegateExecution) {
        log.info("拒绝邮件已发送");
    }
}
