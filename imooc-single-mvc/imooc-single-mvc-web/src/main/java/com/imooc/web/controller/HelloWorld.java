package com.imooc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorld {

    @RequestMapping(value = "/hello")
    public String helloworld(){
        return "hello";
    }
}
