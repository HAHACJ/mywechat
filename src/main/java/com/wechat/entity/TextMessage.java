package com.wechat.entity;

/**
 * ToUserName	开发者微信号
 * FromUserName	发送方帐号（一个OpenID）
 * CreateTime	消息创建时间 （整型）
 * MsgType	text
 * Content	文本消息内容
 * MsgId	消息id，64位整型
 */
public class TextMessage extends BaseMessage {

    //Content	文本消息内容
    private String Content;
    protected long MsgId;

    public TextMessage() {

    }

    public TextMessage(String toUserName, String fromUserName, long createTime, String msgType, long msgId, String content) {
        super(toUserName, fromUserName, createTime, msgType);
        Content = content;

    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}
