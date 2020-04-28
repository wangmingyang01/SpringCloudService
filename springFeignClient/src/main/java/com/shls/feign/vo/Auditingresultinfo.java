package com.shls.feign.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

//付款单融资审核结果表
@ApiModel
public class Auditingresultinfo {
    //唯一标识 UUID
    private String bgyPaymentUuid;
    //付款单号
    private String bgyPaymentNo;
    //不通过原因
    private String qhyfReason;
    //审核结果 0 否 / 1 是
    private int qhyfResult;
    //是否更换发票 0 否 / 1 是
    private int qhyfChange;
    //付款确认书编号
    private String qhyfPayId;
    //多张发票（null不参加序列化）
    List<SendInvoiceInfo> invoiceInfo;

    public Auditingresultinfo(){
        super();
    }

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

    public List<SendInvoiceInfo> getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(List<SendInvoiceInfo> invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }
}
