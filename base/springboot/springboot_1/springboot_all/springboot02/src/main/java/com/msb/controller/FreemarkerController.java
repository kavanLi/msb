package com.msb.controller;

import com.msb.pojo.Emp;
import com.msb.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Ma HaiYang
 * @Description: MircoMessage:Mark_7001
 */
@Controller
public class FreemarkerController {

    // 查询全部员工信息,展示
    @Autowired
    private EmpService empService;

    @RequestMapping("/showEmpMap")
    public ModelAndView testMap(){
        ModelAndView mv=new ModelAndView();
        List<Emp> list = empService.findAll();
        Map<String,Emp> empMap=new HashMap();
        for (Emp emp : list) {
            empMap.put(emp.getEmpno().toString(), emp);
        }
        mv.addObject("empMap", empMap);
        mv.setViewName("showEmpMap");
        return mv;
    }

    @RequestMapping("/showEmp")
    public ModelAndView testList(){
        ModelAndView mv=new ModelAndView();
        List<Emp> list = empService.findAll();
        mv.addObject("empList", list);
        mv.setViewName("showEmp");
        return mv;
    }






    @RequestMapping("/show")
    public String freemarger(Map<String,Object> map){
        map.put("name", "旋涡刘能");
        return "show";
    }
}
