package com.msb.controller;

import com.msb.pojo.Emp;
import com.msb.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class ThymeleafController {

    @Autowired
    private EmpService empService;

    @RequestMapping("/showAllEmp")
    public String showEmp(Map<String, Object> map, HttpServletRequest request, HttpSession session) {
        // 向request域放数据
        request.setAttribute("msg", "requestMessage");
        // 向session域放数据
        session.setAttribute("msg", "sessionMessage");
        // 向application域放数据
        request.getServletContext().setAttribute("msg", "applicationMessage");

        int i =1/0;

        System.out.println("aaa");
        List<Emp> empList = empService.findAll();
        map.put("empList", empList);
        return "showEmp";
    }

    @RequestMapping("/removeEmp")
    public String removeEmp(Integer empno,String ename){
        boolean success =empService.removeEmp(empno,ename);
        return "redirect:showAllEmp";
    }

    @RequestMapping("/showIndex")
    public String showIndex(Map<String, Object> map) {
        map.put("msg", "testMessage");
        return "index";
    }
}
