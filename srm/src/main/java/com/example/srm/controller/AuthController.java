package com.example.srm.controller;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {
    protected static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private String authUrl="https://api.weixin.qq.com/sns/jscode2session?";

    private String appId="wx666218fd0e9a04c4";

    private String appSecret="949b1e68b634a2604a890499e95d8263";

    private final int VERIFYCODE_VALID_SECONDS = 1200;  //20分钟内验证有效

    /**
     * 根据微信传过来的code登录
     * @return
     */
    @RequestMapping(value = "/mservice/auth/loginByWeixin")
    public String loginByWeixin(HttpServletRequest request) {

        String code = request.getParameter("code");
        try {
            //获取openid和sessionKey
            StringBuilder sb = new StringBuilder(authUrl).
                    append("appid=").append(appId).
                    append("&secret=").append(appSecret).
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
        }
        return null;
    }



}
