package com.example.srm.utils;

import net.sf.json.JSONObject;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;



public class WxDecryptUtils {
	public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
 
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static void main(String[] args) {
		JSONObject userInfoObject = WxDecryptUtils.getUserInfo("fWecei4X514AQJnKuQErfZ+xylLrM1ax6eWssAKTlrZKEkXvhWf3AfjmRHD5N/iKTLdVxmIF+TbLqrH4avso8JfSWdZbT6zZ93gXGLvCzo5C2qgW8f67WpwRv6Z6svSgwG9zo5o0z8mnBhs1wviGTZ/AmoZUlPBBEWOF53g7XOE1dZp38sGHGqKrb38qC+kdTwHuhjhOKAM0oLqOBqHKwNxRP+DOj1dxkgu4xqzYlkuKogfHrIQMhUKfJlSeZGmgHpkwSf06hj0liHr04nqXDu/Xb8dNJO19gOnYyE5K6cPfXHnwZQKtxX05zVZiFFnNv6t8i3c8sNSVaNOXxLDrj420iEVhO5ahsX5eDEYUugB/GmJUBAmtrSBaVjCZ46Hilgx68Fxts60Y8CCenbjSl4nVkagr7W5lO1zMX1M3H5asCx0bocTKCDen/oWsHOHafLJKil+AJQn6nkCTo+f2WfpJw6uQDVFAOWsp77SHelk=",
				"udI8rihE3Z+6tG1v1Fcsyw==","UOr1a38hmPrVxc9EYktciw==");
		if(userInfoObject==null) {
			System.out.println("对象为空");
		}
		System.out.println("watermark:"+userInfoObject.getString("watermark"));
	}
}
