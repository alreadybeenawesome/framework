package com.centryOf22th.framework.sms;

import com.centryOf22th.framework.orm.assist.MysqlAssistant;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by louis on 16-11-17.
 */
public abstract class Sms {
    private final static Logger logger = LogManager.getLogger(MysqlAssistant.class);

    protected String userName;
    protected String password;
    protected String smsUrl  = "http://www.smsbao.com";
    protected SmsInfo smsInfo;
    protected List<SmsInfo> smsInfos;
    protected boolean isSendSync=false;
    protected boolean isBatchSend=false;


    protected Sms(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    protected Sms(String userName, String password, String smsUrl) {
        this.userName = userName;
        this.password = password;
        this.smsUrl = smsUrl;
    }

    protected Sms(String userName, String password, String smsUrl, boolean isSendSync, boolean isBatchSend) {
        this.userName = userName;
        this.password = password;
        this.smsUrl = smsUrl;
        this.isSendSync = isSendSync;
        this.isBatchSend = isBatchSend;
    }

    protected Sms(String userName, String password, String smsUrl, SmsInfo smsInfo, List<SmsInfo> smsInfos, boolean isSendSync, boolean isBatchSend) {
        this.userName = userName;
        this.password = password;
        this.smsUrl = smsUrl;
        this.smsInfo = smsInfo;
        this.smsInfos = smsInfos;
        this.isSendSync = isSendSync;
        this.isBatchSend = isBatchSend;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSmsUrl() {
        return smsUrl;
    }

    public void setSmsUrl(String smsUrl) {
        this.smsUrl = smsUrl;
    }

    public SmsInfo getSmsInfo() {
        return smsInfo;
    }

    public void setSmsInfo(SmsInfo smsInfo) {
        this.smsInfo = smsInfo;
    }

    public List<SmsInfo> getSmsInfos() {
        return smsInfos;
    }

    public void setSmsInfos(List<SmsInfo> smsInfos) {
        this.smsInfos = smsInfos;
    }

    public boolean isSendSync() {
        return isSendSync;
    }

    public void setIsSendSync(boolean isSendSync) {
        this.isSendSync = isSendSync;
    }

    public boolean isBatchSend() {
        return isBatchSend;
    }

    public void setIsBatchSend(boolean isBatchSend) {
        this.isBatchSend = isBatchSend;
    }

    protected abstract SmsResult sendSms(SmsInfo smsInfo) throws Exception;

    protected abstract SmsResult sendSmsByBatchMobiles(SmsInfo smsInfo) throws Exception;

    protected abstract boolean checkBalance() throws Exception;

    protected List<SmsResult> batchSendSms(){
        if(StringUtils.isEmpty(this.userName)){
            throw new RuntimeException("sms username is null");
        }
        if(StringUtils.isEmpty(this.password)){
            throw new RuntimeException("sms password is null");
        }

        if(getSmsInfos()==null||getSmsInfos().isEmpty()){
            throw new RuntimeException("sms info list is null");
        }
        Iterator<SmsInfo> iterator =getSmsInfos().iterator();
        ArrayList<SmsResult> smsResults =new ArrayList<>();
        while(iterator.hasNext()){
            SmsInfo smsInfo =iterator.next();
            try {
                SmsResult smsResult =this.sendSms(smsInfo);
                smsResults.add(smsResult);
            } catch (Exception e) {
                logger.error(ExceptionUtils.getStackTrace(e));
            }
        }

        return smsResults;
    }
}
