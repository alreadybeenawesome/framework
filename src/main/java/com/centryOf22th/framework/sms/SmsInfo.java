package com.centryOf22th.framework.sms;

import java.util.List;

/**
 * Created by 余卫家 on 2017/1/3.
 */
public class SmsInfo {

    private String sign; //公司签名
    private String content;
    private String sentMobile;
    private List<String> sentMobiles;


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentMobile() {
        return sentMobile;
    }

    public void setSentMobile(String sentMobile) {
        this.sentMobile = sentMobile;
    }

    public List<String> getSentMobiles() {
        return sentMobiles;
    }

    public void setSentMobiles(List<String> sentMobiles) {
        this.sentMobiles = sentMobiles;
    }
}
