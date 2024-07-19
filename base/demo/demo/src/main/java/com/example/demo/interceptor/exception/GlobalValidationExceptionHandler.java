package com.example.demo.interceptor.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.mashibing.internal.common.constant.ErrorCodeEnum;
import com.mashibing.internal.common.domain.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 校验异常处理类
 */
@RestControllerAdvice
@Order(1)
@Slf4j
public class GlobalValidationExceptionHandler {


    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(BindException e) {
        log.error("{}", e.getLocalizedMessage());
        FieldError objectError = (FieldError) e.getBindingResult().getAllErrors().get(0);
        String responseMsg = new StringBuilder()
                .append("请求参数：")
                .append(objectError.getField())
                .append("-")
                .append(objectError.getDefaultMessage())
                .toString();
        return ResponseResult.fail(ErrorCodeEnum.VALIDATION_EXCEPTION.getCode(), responseMsg);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validationExceptionHandler(MethodArgumentNotValidException e) {
        log.error("{}", e.getLocalizedMessage());
        FieldError objectError = (FieldError) e.getBindingResult().getAllErrors().get(0);
        String responseMsg = new StringBuilder()
                .append("请求参数：")
                .append(objectError.getField())
                .append("-")
                .append(objectError.getDefaultMessage())
                .toString();
        return ResponseResult.fail(ErrorCodeEnum.VALIDATION_EXCEPTION.getCode(), responseMsg);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult ConstraintValidationExceptionHandler(ConstraintViolationException e) {
        log.error("{}", e.getLocalizedMessage());
        Set <ConstraintViolation <?>> constraintViolations = e.getConstraintViolations();
        String message = "";
        for (ConstraintViolation c : constraintViolations) {
            message = c.getMessage();
        }

        return ResponseResult.fail(ErrorCodeEnum.VALIDATION_EXCEPTION.getCode(), message);
    }

}