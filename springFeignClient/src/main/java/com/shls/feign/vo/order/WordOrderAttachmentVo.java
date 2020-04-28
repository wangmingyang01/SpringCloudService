package com.shls.feign.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel
public class WordOrderAttachmentVo {
    @ApiModelProperty("工单Id")
    @NotNull
    private long orderId;

    @ApiModelProperty("其它类型附件数组")
    List<WorkOrderOtherAttachmentForm> attachmentList;

    @ApiModelProperty("上传票据附件集合")
    List<WorkOrderTradingNoteAttachmentForm> tradingNotesList;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<WorkOrderOtherAttachmentForm> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<WorkOrderOtherAttachmentForm> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public List<WorkOrderTradingNoteAttachmentForm> getTradingNotesList() {
        return tradingNotesList;
    }

    public void setTradingNotesList(List<WorkOrderTradingNoteAttachmentForm> tradingNotesList) {
        this.tradingNotesList = tradingNotesList;
    }
}
