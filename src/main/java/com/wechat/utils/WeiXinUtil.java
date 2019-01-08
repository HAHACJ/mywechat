package com.wechat.utils;

import com.wechat.entity.AccessToken;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class WeiXinUtil {

    private static final String APP_ID = "";

    private static final String APP_SECRECT = "";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Autowired
    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisTemplate<String, String> redis;


    @PostConstruct
    public void init() {
        WeiXinUtil.redisTemplate = this.redis;
    }

    /**
     * 处理get请求
     *
     * @param url
     * @return
     */
    public static JSONObject doGet(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.fromObject(result);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * 处理post
     *
     * @param url
     * @param out
     * @return
     */
    public static JSONObject doPost(String url, String out) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(out, "utf-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), "utf-8");
            jsonObject = JSONObject.fromObject(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 从请求中获取token
     *
     * @return
     */
    private static AccessToken getToken() {
        log.info("开始请求获取token");
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRECT);
        JSONObject jsonObject = doGet(url);

        if (jsonObject != null) {
            try {
                token.setAccess_token(jsonObject.getString("access_token"));
                token.setExpires_in(jsonObject.getInt("expires_in"));
                String access_token = jsonObject.getString("access_token");
                redisTemplate.opsForValue().set("access_token", access_token, 60 * 60 * 2, TimeUnit.SECONDS);
            } catch (Exception e) {
                log.error("{}", jsonObject);
                e.printStackTrace();
            }

        }
        log.info("请求的token-----{}", token.getAccess_token());
        return token;
    }

    /**
     * 从缓存中获取token
     *
     * @return
     */
    public static String getCashAccessToken() {
        log.info("开始从缓存中获取token");
        String access_token = redisTemplate.opsForValue().get("access_token");
        if (StringUtils.isBlank(access_token)) {
            log.info("缓存中没有token");
            AccessToken accessToken = getToken();
            access_token = accessToken.getAccess_token();
            return access_token;
        }
        log.info("缓存中的token----{}", access_token);
        return access_token;
    }


    /**
     *
     * @param file
     * @param accessToken
     * @param type
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public static String saveMediaId(File file, String accessToken, String type, String key, String reultKey, long timeout, TimeUnit timeUnit) {

        JSONObject jsonObject = UploadUtil.upload(file, accessToken, type);
        String mediaId = jsonObject.getString(reultKey);
        redisTemplate.opsForValue().set(key, mediaId, timeout, timeUnit);
        return mediaId;
    }

    /**
     *
     * @param resourcePath
     * @param messageType
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public static String getMediaId(String resourcePath, String messageType, String key, String reultKey, long timeout, TimeUnit timeUnit) {
        String accessToken = WeiXinUtil.getCashAccessToken();
        String mediaId = redisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(mediaId)) {
            log.info("缓存中没有mediaId,准备上传素材");
            File file = null;
            try {
                file = ResourceUtils.getFile(resourcePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mediaId = WeiXinUtil.saveMediaId(file, accessToken, messageType, key, reultKey, timeout, timeUnit);
        }
        return mediaId;
    }

}
