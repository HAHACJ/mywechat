package com.wechat.service;

import com.wechat.entity.TextMessage;
import com.wechat.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TextMessageService implements BaseService <TextMessage> {

    @Override
    public String initMessage(Map<String, String> map) {

        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        long msgId = Long.valueOf(map.get("MsgId"));

        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(msgType);
        text.setMsgId(msgId);
        text.setCreateTime(new Date().getTime());
        text.setContent("你发送的消息是：" + content);
        return MessageUtil.baseObj2XML(text);
    }

}
