package com.wanshu.wanshu.annotation;

import com.wanshu.wanshu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 账号唯一的校验器
 */
public class UserNameConstraintValidator implements ConstraintValidator<UserNameUniqueValid,String> {

    @Autowired
    private IUserService userService;

    /**
     * 初始化的方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(UserNameUniqueValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     *
     * @param value 客户提交的userName
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 校验账号是否合法：
        System.out.println("value = " + value);
        return userService.checkUserName(value);
    }
}
