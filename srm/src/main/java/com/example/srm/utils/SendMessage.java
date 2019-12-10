package com.example.srm.utils;

import com.example.srm.pojo.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;

public class SendMessage {
    static boolean flag = false;

    static  String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?";

    static private String token_url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=AppId&secret=AppSecret";

    static private String appID="wx666218fd0e9a04c4";

    static private String secret="949b1e68b634a2604a890499e95d8263";

    private static String authUrl="https://api.weixin.qq.com/sns/jscode2session?";

    public static boolean sendTemplateMsg(String token,String template) throws IOException {
        StringBuilder sb = new StringBuilder(requestUrl)
                .append("access_token=").append(template);
        String resultStr = HttpUtil.postByJSON(sb.toString(),template);
        JSONObject jsonResult = JSONObject.fromObject(resultStr);
        if (jsonResult != null) {
            Integer errorCode = jsonResult.getInt("errcode");
            String errorMessage = jsonResult.getString("errmsg");
            if (errorCode == 0) {
                flag = true;
            } else {
                System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 获取接口访问凭证
     *
     * @param appid
     *            凭证
     * @param appsecret
     *            密钥
     * @return
     */
    public static Token getToken() {
        Token token = null;
        String requestUrl = token_url.replace("AppId", appID).replace("AppSecret", secret);
        // 发起GET请求获取凭证
        String resultStr = httpsRequest(requestUrl, "GET", null);
        JSONObject jsonObject = JSONObject.fromObject(resultStr);

        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
//                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"),
//                        jsonObject.getString("errmsg"));
            }
        }
        return token;
    }
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
//            logger.error("连接超时：{}");
        } catch (Exception e) {
//            logger.error("https请求异常：{}");
        }
        return null;
    }

    /**
     * 获取openId
     */
    public static  String getOpenId(String code) {
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
        }
        return null;
    }



}
