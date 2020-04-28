package com.shls.feign.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AuditingresultinfoModel {
    private String bgyPaymentUuid;
    private String bgyPaymentNo;
    private String qhyfReason;
    private int qhyfResult;
    private int qhyfChange;
    private String qhyfPayId;
    private String invoiceInfoJson;

    public String getBgyPaymentUuid() {
        return bgyPaymentUuid;
    }

    public void setBgyPaymentUuid(String bgyPaymentUuid) {
        this.bgyPaymentUuid = bgyPaymentUuid;
    }

    public String getBgyPaymentNo() {
        return bgyPaymentNo;
    }

    public void setBgyPaymentNo(String bgyPaymentNo) {
        this.bgyPaymentNo = bgyPaymentNo;
    }

    public String getQhyfReason() {
        return qhyfReason;
    }

    public void setQhyfReason(String qhyfReason) {
        this.qhyfReason = qhyfReason;
    }

    public int getQhyfResult() {
        return qhyfResult;
    }

    public void setQhyfResult(int qhyfResult) {
        this.qhyfResult = qhyfResult;
    }

    public int getQhyfChange() {
        return qhyfChange;
    }

    public void setQhyfChange(int qhyfChange) {
        this.qhyfChange = qhyfChange;
    }

    public String getQhyfPayId() {
        return qhyfPayId;
    }

    public void setQhyfPayId(String qhyfPayId) {
        this.qhyfPayId = qhyfPayId;
    }

    public String getInvoiceInfoJson() {
        return invoiceInfoJson;
    }

    public void setInvoiceInfoJson(String invoiceInfoJson) {
        this.invoiceInfoJson = invoiceInfoJson;
    }
}
