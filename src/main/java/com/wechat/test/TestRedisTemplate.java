package com.wechat.test;


import com.wechat.entity.BaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Test
    public void test1() {
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setFromUserName("张三");
        redisTemplate.opsForSet().add("name", baseMessage);
        log.info("设置完毕！");
    }

    @Test
    public void test3() {
        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setFromUserName("王五");
        redisTemplate.opsForValue().set("name2", baseMessage);
        log.info("设置完毕！");
    }

    @Test
    public void test2() {
        Object object = redisTemplate.opsForValue().get("name2");
        System.out.println(object);
        log.info("object", object);
    }



}
