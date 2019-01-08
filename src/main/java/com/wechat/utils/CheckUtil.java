package com.wechat.utils;

import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * 验证消息的确来自微信服务器
 */
@Component
public class CheckUtil {

    private static final String token = "mywechathaha";

    public static boolean checkSignature(String signature, String timestamp, String nonce){
        //排序
        String[] strArr = new String[]{token, timestamp, nonce};
        Arrays.sort(strArr);

        //生成字符串
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < strArr.length; i++){
            sb.append(strArr[i]);
        }

        //sha1加密
        String sha1 = SHA1.encode(sb.toString());

        return signature.equals(sha1);
    }
}
