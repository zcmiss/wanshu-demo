package com.wanshu.wanshu.exception;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统全局异常处理器
 */
@Slf4j
@ControllerAdvice(basePackages =  {"com.wanshu.wanshu.controller"})
public class GlobalExceptionControllerAdvice {

    /**
     * 针对性的处理 校验数据的异常
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public String handleValidException(BindException e,
                                       RedirectAttributes redirectAttributes,
                                       HttpServletRequest request,
                                       Model model){
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String,String> map = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

       //  model.addAttribute("errors",map);
        log.error("抛出的异常类型{},异常信息{}",e,e.getMessage());
        // 如果是更新操作  需要通过
        // 在请求中获取如果出现异常 对应的调整页面
        String errorPath = request.getParameter("errorPath");
        if(errorPath.toUpperCase().contains("UPDATE")){
            // 通过Session方法来绑定错误信息
            redirectAttributes.addFlashAttribute("errors",map);
            return "redirect:"+errorPath;
        }
        model.addAttribute("errors",map);
        return errorPath;
    }
}
