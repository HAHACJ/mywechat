package com.wechat.service;

import com.wechat.entity.Image;
import com.wechat.entity.ImageMessage;
import com.wechat.utils.MessageUtil;
import com.wechat.utils.WeiXinUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class ImgMessageService implements BaseService<ImageMessage> {
    @Override
    public String initMessage(Map<String, String> map) {

        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");

        long msgId = Long.valueOf(map.get("MsgId"));
        long createTime = Long.valueOf(new Date().getTime());

        ImageMessage imageMessage = new ImageMessage();
        Image image = new Image();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MessageUtil.UPLOAD_MEDIA_TYPE_IMAGE);
        imageMessage.setCreateTime(createTime);
        String resource = "classpath:a.jpg";

        image.setMediaId(WeiXinUtil.getMediaId(resource, MessageUtil.UPLOAD_MEDIA_TYPE_IMAGE,
                                            "img_id", "media_id", 1, TimeUnit.DAYS));
        imageMessage.setImage(image);
        return MessageUtil.baseObj2XML(imageMessage);
    }

}
