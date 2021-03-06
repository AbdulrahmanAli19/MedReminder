package com.example.itijavaproject.pojo.model;

import java.io.Serializable;

public class Request implements Serializable {
    private String senderMail;
    private String receiverMail;
    private String senderUid;
    private String key;
    private boolean isShared;
    private boolean state = false;

    public Request() {

    }

    public Request(String senderMail, String receiverMail, String senderUid, boolean isShared, boolean state) {
        this.senderMail = senderMail;
        this.receiverMail = receiverMail;
        this.senderUid = senderUid;
        this.isShared = isShared;
        this.state = state;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getReceiverMail() {
        return receiverMail;
    }

    public void setReceiverMail(String receiverMail) {
        this.receiverMail = receiverMail;
    }

    public boolean isShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
