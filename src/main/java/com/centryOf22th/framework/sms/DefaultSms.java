package com.centryOf22th.framework.sms;

/**
 * Created by louis on 16-11-17.
 */
public final class DefaultSms extends Sms{

    public DefaultSms(String username,String password) {
        super(username,password);
    }

    public DefaultSms(String userName, String password, String smsUrl) {
        super(userName, password, smsUrl);
    }

    public static void main(String[] args) {
        Sms sms =new DefaultSms("","","");
    }
}
