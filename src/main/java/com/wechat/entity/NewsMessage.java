package com.wechat.entity;

import java.util.List;

public class NewsMessage extends BaseMessage {

    private int AriticleCount;

    private List<News> Articles;

    public int getAriticleCount() {
        return AriticleCount;
    }

    public void setAriticleCount(int ariticleCount) {
        AriticleCount = ariticleCount;
    }

    public List<News> getArticles() {
        return Articles;
    }

    public void setArticles(List<News> articles) {
        Articles = articles;
    }
}
