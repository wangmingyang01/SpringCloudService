package com.shls.feign.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

//融资资产包
@ApiModel
public class AssetPackage {
    //批次
    @ApiModelProperty("批次")
    private String batchno;
    //业务类型编码
    @ApiModelProperty("业务类型编码")
    private String businessCode;
    //业务类型名称
    @ApiModelProperty("业务类型名称")
    private String businessName;
    //保理公司名称
    @ApiModelProperty("保理公司名称")
    private String drName;
    //期数
    @ApiModelProperty("期数")
    private String period;
    //金额(double类型接口报参数错误)
    @ApiModelProperty("金额")
    private String amount;
    //多个uuid
    private List<Uuids> uuids;

    public AssetPackage(){
        super();
    }

    public String getBatchno() {
        return batchno;
    }

    public void setBatchno(String batchno) {
        this.batchno = batchno;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List<Uuids> getUuids() {
        return uuids;
    }

    public void setUuids(List<Uuids> uuids) {
        this.uuids = uuids;
    }
}

