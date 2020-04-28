package com.shls.feign.vo;

//融资批次档案表
public class FinancingBatchInfo {
    //收款账户
    private String bankaccount;
    //开户名
    private String bankname;
    //批次
    private String batchno;
    //金融公司
    private String company;
    //到期日期
    private String expirydate;
    //期数
    private String periods;
    //来源系统  传空 根据密钥获得系统编码
    private String sourcesys;

    public FinancingBatchInfo(){
        super();
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getSourcesys() {
        return sourcesys;
    }

    public void setSourcesys(String sourcesys) {
        this.sourcesys = sourcesys;
    }
}

