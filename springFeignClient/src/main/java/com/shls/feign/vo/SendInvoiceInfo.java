package com.shls.feign.vo;

public class SendInvoiceInfo {
    //唯一标识 UUID
    private String pk_father;
    //发票代码
    private String qhyfInvoiceCode;
    //发票号码
    private String qhyfInvoiceNo;
    //发票金额
    private double qhyfInvoiceAmount;
    //开票日期
    private String qhyfInvoiceDate;
    //发票名称 如,深圳增值税普通发票
    private String qhyfInvoiceName;

    public SendInvoiceInfo(){
        super();
    }

    public String getPk_father() {
        return pk_father;
    }

    public void setPk_father(String pk_father) {
        this.pk_father = pk_father;
    }

    public String getQhyfInvoiceCode() {
        return qhyfInvoiceCode;
    }

    public void setQhyfInvoiceCode(String qhyfInvoiceCode) {
        this.qhyfInvoiceCode = qhyfInvoiceCode;
    }

    public String getQhyfInvoiceNo() {
        return qhyfInvoiceNo;
    }

    public void setQhyfInvoiceNo(String qhyfInvoiceNo) {
        this.qhyfInvoiceNo = qhyfInvoiceNo;
    }

    public double getQhyfInvoiceAmount() {
        return qhyfInvoiceAmount;
    }

    public void setQhyfInvoiceAmount(double qhyfInvoiceAmount) {
        this.qhyfInvoiceAmount = qhyfInvoiceAmount;
    }

    public String getQhyfInvoiceDate() {
        return qhyfInvoiceDate;
    }

    public void setQhyfInvoiceDate(String qhyfInvoiceDate) {
        this.qhyfInvoiceDate = qhyfInvoiceDate;
    }

    public String getQhyfInvoiceName() {
        return qhyfInvoiceName;
    }

    public void setQhyfInvoiceName(String qhyfInvoiceName) {
        this.qhyfInvoiceName = qhyfInvoiceName;
    }
}
