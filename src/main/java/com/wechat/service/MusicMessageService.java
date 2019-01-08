package com.wechat.service;

import com.wechat.entity.Music;
import com.wechat.entity.MusicMessage;
import com.wechat.utils.MessageUtil;
import com.wechat.utils.WeiXinUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MusicMessageService implements BaseService<MusicMessage> {

    @Override
    public String initMessage(Map<String, String> map) {

        String title = "sounds of china";
        String descrip = "一段音乐";
        //springboot通过网络请求访问静态资源默认在static文件夹中
        String musicURL = "http://wechatcustomer.ngrok.xiaomiqiu.cn/music/b.mp3";
        String HQMusicURL = "http://wechatcustomer.ngrok.xiaomiqiu.cn/music/a.mp3";
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");

        long msgId = Long.valueOf(map.get("MsgId"));
        long createTime = Long.valueOf(new Date().getTime());

        MusicMessage musicMessage = new MusicMessage();
        Music music = new Music();

        music.setTitle(title);
        music.setDescription(descrip);
        music.setMusicUrl(musicURL);
        music.setHQMusicUrl(HQMusicURL);
        String resource = "classpath:a.jpg";
        String id = WeiXinUtil.getMediaId(resource, MessageUtil.UPLOAD_MEDIA_TYPE_MUSIC, "music_id",
                                    MessageUtil.REP_MESSAGE_TYPE_MUSIC, 1, TimeUnit.DAYS);
        music.setThumbMediaId(id);

        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);

        musicMessage.setMsgType(MessageUtil.MESSAGE_TYPE_MUSIC);
        musicMessage.setCreateTime(createTime);
        musicMessage.setMusic(music);

        return MessageUtil.baseObj2XML(musicMessage);
    }

}
