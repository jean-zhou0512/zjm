package com.example.srm.controller;

import com.example.srm.pojo.Department;
import com.example.srm.service.depeartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class helloWorld {
    @Autowired
    depeartmentService depeartmentservice;

//    @ResponseBody
    @RequestMapping("/helloWorld")
    public String helloWorld(){
        System.out.println("测试");
        return "index";
    }

    @ResponseBody
    @RequestMapping("/depeartment")
    public List<Department> depeartment(Map<String ,Object> paramMap){
        List<Department> getlist=depeartmentservice.getList(paramMap);
        return getlist;
    }

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("测试");
        return "hello";
    }

    @RequestMapping("/test01")
    public String test01(HttpSession session){
        session.setAttribute("user","zjm");
        return "hello";
    }


}
