package com.shls.config;

public class LoggedUser {
    /** id*/
    private long id;
    /** 登录名*/
    private String loginAccount;
    /** 登录类型*/
    private String type;
    /** 验证时的token*/
    private String token;

    public LoggedUser() { }

    public LoggedUser(long id, String loginAccount, String type, String token) {
        this.id = id;
        this.loginAccount = loginAccount;
        this.type = type;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoggedUser{" +
                "id=" + id +
                ", loginAccount='" + loginAccount + '\'' +
                ", type='" + type + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
