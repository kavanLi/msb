package com.msb.controller;

import com.msb.pojo.Emp;
import com.msb.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @RequestMapping("findAll")
    @ResponseBody
    public List<Emp> findAll(){
        return empService.findAll();
    }


    @RequestMapping("findByPage/{pageNum}/{pageSize}")
    @ResponseBody
    public List<Emp> findByPage(@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        return empService.findByPage(pageNum,pageSize);
    }


}
