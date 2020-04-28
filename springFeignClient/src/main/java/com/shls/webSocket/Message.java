package com.shls.webSocket;

public class Message {
    private int code;
    private String type;
    private String content;

    public Message(int code, String type, String content){
        this.code = code;
        this.type = type;
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
}
