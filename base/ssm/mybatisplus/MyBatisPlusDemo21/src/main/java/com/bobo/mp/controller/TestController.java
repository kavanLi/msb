package com.bobo.mp.controller;

import java.util.HashMap;
import java.util.Map;

import com.bobo.mp.exception.BaseException;
import org.json.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-13 09:43
 * To change this template use File | Settings | File and Code Templates.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping(value = "")
    public Map <String, String> test() {
        System.out.println(123123123);
        Map <String, String> errors = new HashMap <>();
        errors.put("123", "1231");
        return errors;
    }


    @PostMapping("/test1")
    public ResponseEntity <JSONObject> test1() {
        JSONObject result = new JSONObject();
        result.put("code", "fail");
        result.put("msg", "exception.getMessage()");
        return ResponseEntity.ok(result);
    }
}
