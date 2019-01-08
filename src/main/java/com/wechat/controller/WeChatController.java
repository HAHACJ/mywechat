package com.wechat.controller;

import com.wechat.service.ImgMessageService;
import com.wechat.service.MusicMessageService;
import com.wechat.service.NewsMessageService;
import com.wechat.service.TextMessageService;
import com.wechat.utils.CheckUtil;
import com.wechat.utils.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@RestController
public class WeChatController {

    @Autowired
    TextMessageService textMessageService;

    @Autowired
    ImgMessageService imgMessageService;

    @Autowired
    MusicMessageService musicMessageService;

    @Autowired
    NewsMessageService newsMessageService;

    /**
     * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp	时间戳
     * nonce	随机数
     * echostr	随机字符串
     */

    @GetMapping("/wx")
    public void getToken(HttpServletRequest request, HttpServletResponse response) {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        boolean result = CheckUtil.checkSignature(signature, timestamp, nonce);


        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (result) {
                out.write(echostr);
            }
        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            out.close();
        }

    }

    @PostMapping("/wx")
    public void chat(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        Map<String, String> map = MessageUtil.xml2Map(request);
        System.out.println(map);
        String content = map.get("Content");

        String msgType = map.get("MsgType");
        String message = null;

        try {
            switch (msgType) {
                case MessageUtil.MESSAGE_TYPE_TEXT:
                    if ("1".equals(content)) {
                        message = imgMessageService.initMessage(map);
                        System.out.println(message);
                    }
                    if ("2".equals(content)) {

                        message = textMessageService.initMessage(map);
                        System.out.println(message);
                    }

                    if ("3".equals(content)) {

                        message = musicMessageService.initMessage(map);
                        System.out.println(message);
                    }

                    if ("4".equals(content)) {

                        message = newsMessageService.initMessage(map);
                        System.out.println(message);
                    }

                    if ("5".equals(content)) {

                        message = newsMessageService.initManyMessages(map);
                        System.out.println(message);
                    }

                    break;

            }

            out = response.getWriter();
            out.print(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
