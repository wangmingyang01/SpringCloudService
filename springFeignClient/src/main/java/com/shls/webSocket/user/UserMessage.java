package com.shls.webSocket.user;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserMessage implements Serializable {
    private static final long serialVersionUID = 1569310145026L;

    private String sendUserId;
    private String receiverUserId;
    private String type;
    private String content;
    private Long createTime;

    /** 提供无参的构造方法,支持jackson反序列化*/
    public UserMessage(){
    }

    public UserMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public UserMessage(String sendUserId, String receiverUserId, String type, String content, Long createTime) {
        this.sendUserId = sendUserId;
        this.receiverUserId = receiverUserId;
        this.type = type;
        this.content = content;
        this.createTime = createTime;
    }

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(String receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJSONString(){
        return JSONObject.toJSONString(this);
    }
}
