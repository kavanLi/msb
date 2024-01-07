package com.msb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msb.pojo.Person;
import com.msb.pojo.Pet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@RestController
public class AjaxController {

    /*
     * @ResponseBody
     * 1方法的返回值不在作为界面跳转依据,而已直接作为返回的数据
     * 2将方法的返回的数据自动使用ObjectMapper转换为JSON
     */
    @RequestMapping("testAjax")
    public Pet testAjax(Person p) throws JsonProcessingException {
        System.out.println(p);
        Pet pet =new Pet("Tom","cat");
        return pet;
    }
}
