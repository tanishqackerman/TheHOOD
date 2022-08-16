package com.meow.thehood.Models;

public class Messages {

    String msg, senderusername;
    long timestamp;

    public Messages() {
    }

    public Messages(String msg, String senderusername, long timestamp) {
        this.msg = msg;
        this.senderusername = senderusername;
        this.timestamp = timestamp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSenderusername() {
        return senderusername;
    }

    public void setSenderusername(String senderusername) {
        this.senderusername = senderusername;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
