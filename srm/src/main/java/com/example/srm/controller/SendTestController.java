package com.example.srm.controller;

import com.example.srm.pojo.Token;
import com.example.srm.pojo.wxsmallTemplateParam;
import com.example.srm.utils.SendMessage;
import com.example.srm.utils.wxsmallTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class SendTestController {
    @RequestMapping(value="/SendTest")
    @ResponseBody
    public void SendTest(HttpServletRequest request){
        String formId=request.getParameter("formId");
        String code=request.getParameter("code");
        String openId=request.getParameter("openId");
        wxsmallTemplate wxtem=new wxsmallTemplate();
        //模板id
        wxtem.setTemplateId("DLTquw2imf61y6XTxulffDfphhR1xNAZa-CGb3NwWbk");
        //推送给用户 openId
        wxtem.setToUser(openId);
        //formId
        wxtem.setForm_id(formId);
        //用户点击后调到那个小程序页面
        wxtem.setPage("");
        List<wxsmallTemplateParam> params=new ArrayList<>();
        params.add(new wxsmallTemplateParam("keyword1","尊敬的客户，您的用款申请已提交，稍后会有客户经理[131xxxxxxx]与您电话联系，非常感谢您对粤荣华的信任！"));
        params.add(new wxsmallTemplateParam("keyword2",new Date().toString()));
        params.add(new wxsmallTemplateParam("keyword3","资金周转"));
        params.add(new wxsmallTemplateParam("keyword4","周小明"));
        params.add(new wxsmallTemplateParam("keyword5","13426887484"));
        wxtem.setTemplateParamList(params);
        Token token= SendMessage.getToken();
        String accessToken =token.getAccessToken();
        //请求接口，推送成功
        boolean result=SendMessage.sendTemplateMsg(accessToken,wxtem.toJSON());
        if(result){
            System.out.println("推送成功");
        }else{
            System.out.println("推送失败");
        }

    }
}
