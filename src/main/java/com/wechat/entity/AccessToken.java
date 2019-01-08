package com.wechat.entity;

public class AccessToken {

//  AppID  wx52b5ae1c9b87de7e

//  AppSecret bafb8e90eb7aa9cbb2f842f0cfc31114

    //凭证
    private String access_token;
    //凭证有效时间
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
