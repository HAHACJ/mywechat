package com.wechat.utils;


import com.thoughtworks.xstream.XStream;
import com.wechat.entity.BaseMessage;
import com.wechat.entity.ImageMessage;
import com.wechat.entity.News;
import com.wechat.entity.NewsMessage;
import com.wechat.entity.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析XML数据包结构
 */
@Slf4j
public class MessageUtil {

    public static final String MESSAGE_TYPE_TEXT = "text";

    public static final String UPLOAD_MEDIA_TYPE_MUSIC = "thumb";

    public static final String REP_MESSAGE_TYPE_MUSIC = "thumb_media_id";

    public static final String MESSAGE_TYPE_MUSIC = "music";

    public static final String UPLOAD_MEDIA_TYPE_IMAGE = "image";

    public static final String MESSAGE_TYPE_NEWS = "news";

    /**
     * 解析xml
     * @param request
     * @return
     */
    public static Map<String, String> xml2Map(HttpServletRequest request){
        Map<String, String> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();
        InputStream input = null;

        try {
            input = request.getInputStream();
            Document document = saxReader.read(input);
            Element root = document.getRootElement();
            List<Element> list = root.elements();
            list.forEach(l -> {
                map.put(l.getName(), l.getText());
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;
    }


    /**
     * 将BaseMessage的实例转换成xml
     * @param messageType
     * @return
     */
    public static String baseObj2XML(Object messageType){

        if (messageType instanceof BaseMessage) {
            XStream xstream = new XStream();
            xstream.alias("xml", messageType.getClass());
            return xstream.toXML(messageType);
        }
        log.warn("{}不是BaseMessage实例", messageType);
        return null;
    }


    /**
     * 将图文消息转成xml
     * @param newsMessage
     * @return
     */
    public static String newsMessage2XML(NewsMessage newsMessage) {
        XStream xstream = new XStream();
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new News().getClass());
        return xstream.toXML(newsMessage);
    }


}
