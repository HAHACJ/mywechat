package com.wechat.test;

import com.wechat.utils.MessageUtil;
import com.wechat.utils.UploadUtil;
import com.wechat.utils.WeiXinUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestToken {

    @Test
    public void getToken() {
        String accessToken = WeiXinUtil.getCashAccessToken();
        System.out.println("token:" + accessToken);
    }

    @Test
    public void testImage() throws FileNotFoundException {
        String accessToken = WeiXinUtil.getCashAccessToken();
        System.out.println("token:" + accessToken);
        String resource = "classpath:a.jpg";

        File file = ResourceUtils.getFile(resource);
        JSONObject jsonObject = UploadUtil.upload(file, accessToken, MessageUtil.UPLOAD_MEDIA_TYPE_MUSIC);
//        String id = WeiXinUtil.getMediaId(resource, MessageUtil.UPLOAD_MEDIA_TYPE_MUSIC, "music_id",
//                "thumb_media_id", 1, TimeUnit.DAYS);
        System.out.println(jsonObject);
    }
}
