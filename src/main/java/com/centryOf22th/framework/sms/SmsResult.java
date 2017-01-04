package com.centryOf22th.framework.sms;

/**
 * Created by ”‡Œ¿º“ on 2017/1/3.
 */
public class SmsResult {

    private boolean success = false;
    private String errorCode;
    private String errorMsg;
    private String smsId;
    private String successCnt;
    private String errorCnt;
    private String blackCnt;
    private String mobile;
    private String content;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getSuccessCnt() {
        return successCnt;
    }

    public void setSuccessCnt(String successCnt) {
        this.successCnt = successCnt;
    }

    public String getErrorCnt() {
        return errorCnt;
    }

    public void setErrorCnt(String errorCnt) {
        this.errorCnt = errorCnt;
    }

    public String getBlackCnt() {
        return blackCnt;
    }

    public void setBlackCnt(String blackCnt) {
        this.blackCnt = blackCnt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
