package com.example.demo.controller;

import com.mashibing.internal.common.domain.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author: kavanLi-R7000
 * @create: 2024-03-26 16:04
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@Slf4j
@Validated
@RequestMapping("/validation/")
public class ValidationController {


    /**
     * GlobalValidationExceptionHandler.class 定义优雅返回
     * @param
     * @return
     */
    @PostMapping("/test")
    public String applyPersonalRealNameBindCard1010(User user) {
        return "123";
    }



}
