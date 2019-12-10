package com.example.srm.controller;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MessagePush {

    static private String appID="wx666218fd0e9a04c4";

    static private String secret="949b1e68b634a2604a890499e95d8263";

    private static String authUrl="https://api.weixin.qq.com/sns/jscode2session?";

    @RequestMapping("/openPushMsg")
    @ResponseBody
    public String openPushMsg(HttpServletRequest request, HttpServletResponse response) {
        String code =request.getParameter("code");
        try {
            //获取openid和sessionKey
            StringBuilder sb = new StringBuilder(authUrl).
                    append("appid=").append(appID).
                    append("&secret=").append(secret).
                    append("&js_code=").append(code).
                    append("&grant_type=authorization_code");

            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(sb.toString(), String.class);
            JSONObject jsonObject = JSONObject.fromObject(result);
            if (jsonObject.containsKey("errcode")) {
                throw new Exception("出错啦");
            }
            String sessionKey = jsonObject.getString("session_key");
            String openid = jsonObject.getString("openid");
            return openid;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
