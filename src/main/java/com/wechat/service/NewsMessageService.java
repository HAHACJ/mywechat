package com.wechat.service;

import com.wechat.entity.News;
import com.wechat.entity.NewsMessage;
import com.wechat.utils.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class NewsMessageService implements BaseService<NewsMessage> {
    @Override
    public String initMessage(Map<String, String> map) {

        List<News> newsList = new ArrayList<>();

        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("it民工学习网站");
        news.setDescription("csdn国内搬运工");
        news.setPicUrl("http://wechatcustomer.ngrok.xiaomiqiu.cn/pic/5.jpg");
        news.setUrl("http://www.baidu.com");
        newsList.add(news);
        return xmlResult(map, newsMessage, newsList);

    }


    public String initManyMessages(Map<String, String> map) {

        List<News> newsList = new ArrayList<>();

        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("it民工学习网站");
        news.setDescription("csdn国内搬运工");
        news.setPicUrl("http://wechatcustomer.ngrok.xiaomiqiu.cn/pic/2.jpg");
        news.setUrl("https://www.csdn.net/");
        newsList.add(news);

        News news2 = new News();
        news2.setTitle("it民工学习网站");
        news2.setDescription("csdn国内搬运工");
        news2.setPicUrl("http://wechatcustomer.ngrok.xiaomiqiu.cn/pic/3.jpg");
        news2.setUrl("https://www.csdn.net/");
        newsList.add(news2);

        News news3 = new News();
        news3.setTitle("it民工学习网站");
        news3.setDescription("csdn国内搬运工");
        news3.setPicUrl("http://wechatcustomer.ngrok.xiaomiqiu.cn/pic/4.jpg");
        news3.setUrl("https://www.csdn.net/");
        newsList.add(news3);

        return xmlResult(map, newsMessage, newsList);

    }


    private String xmlResult(Map<String, String> map, NewsMessage newsMessage, List<News> newsList) {
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        long msgId = Long.valueOf(map.get("MsgId"));
        long createTime = Long.valueOf(new Date().getTime());

        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setCreateTime(createTime);
        newsMessage.setMsgType(MessageUtil.MESSAGE_TYPE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setAriticleCount(newsList.size());
        return MessageUtil.newsMessage2XML(newsMessage);
    }
}
