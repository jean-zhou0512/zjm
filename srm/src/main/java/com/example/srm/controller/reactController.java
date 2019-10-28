package com.example.srm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class reactController {

    @RequestMapping("/firstReact")
    public String firstReact(){
       return "firstReact";
    }

    @RequestMapping("/jsxTest")
    public String jsxTest(){
        return "02_JSX";
    }

    @RequestMapping("/react03")
    public String react03(){
        return "03_React";
    }

    @RequestMapping("/reactComponent")
    public String reactComponent(){
        return "ReactComponent";
    }

    @RequestMapping("/react04")
    public String react04(){
        return "04_React";
    }

    @RequestMapping("/countReact")
    public  String countReact(){
        return "countReact";
    }

    @RequestMapping("/propsReact")
    public String propsReact(){
        return "/propsReact";
    }

    @RequestMapping("/refsReact")
    public  String refsReact(){
        return "refsReact";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/react11")
    public  String react11(){
        return "11_React";
    }

    @RequestMapping("/formComponent")
    public  String formComponent(){
        return "form_component";
    }

    @RequestMapping("/ComponentLife")
    public String ComponentLife(){
        return "Life_Component";
    }

    @RequestMapping("/dateComponent")
    public String dateComponent(){
        return "date_component";
    }

    @RequestMapping("/reactAxios")
    public String reactAxios(){
        return "React_Axios";
    }

    @RequestMapping("/myReactAxios")
    public String myReactAxios(){
        return "MyReact_Axios";
    }
}
