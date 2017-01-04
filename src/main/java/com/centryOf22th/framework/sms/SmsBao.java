package com.centryOf22th.framework.sms;

/**
 * Created by louis on 16-11-17.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ∂Ã–≈±¶ µœ÷
 */
public class SmsBao extends Sms {

    private final String interfaceUrl = "http://www.smsbao.com/";

    public SmsBao(String username, String password) {
        super(username, password);
    }

    public SmsBao(String userName, String password, String smsUrl) {
        super(userName, password, smsUrl);
    }

    @Override
    protected SmsResult sendSms(SmsInfo smsInfo) throws Exception {

        return null;
    }

    @Override
    protected SmsResult sendSmsByBatchMobiles(SmsInfo smsInfo) throws Exception {
        return null;
    }

    @Override
    protected boolean checkBalance() throws Exception {
        Sms smsBao = new SmsBao(this.getUserName(), md5(this.getPassword()));

        return false;
    }

    private String md5(String plainText) {
        StringBuffer buf = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buf.toString();
    }


    private String buildParam(String url, Map<String, Object> params) {
        StringBuffer httpParam = new StringBuffer(url);
        if (params != null && params.size() > 0) {
            httpParam.append("?");
            Set set = params.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpParam.append(entry.getKey())
                        .append("=")
                        .append(entry.getValue())
                        .append("&");

            }
            httpParam.delete(httpParam.lastIndexOf("&"),httpParam.length());
            return httpParam.toString();
        }
        return url;
    }
}
