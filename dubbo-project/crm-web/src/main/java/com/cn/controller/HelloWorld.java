package com.cn.controller;

import com.cn.api.ItemService;
import com.cn.entity.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorld {

    @Autowired
    ItemService itemService;

    @RequestMapping(value = "/hello")
    @ResponseBody
    public String helloworld(){
        return "hello";
    }

    @RequestMapping(value="/getItem")
    @ResponseBody
    public Items getItem(){
        Items items=itemService.getItem(1);
        return items;
    }
}
