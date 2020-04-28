package com.shls.feign.vo.order;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class WorkOrderTradingNoteAttachmentForm {
    @ApiModelProperty("票据附件id")
    private long tradingNotesId;

    @ApiModelProperty("票据附件")
    private MultipartFile file;

    @ApiModelProperty("附件类型")
    private String type;

    public WorkOrderTradingNoteAttachmentForm(long tradingNotesId, MultipartFile file, String type) {
        this.tradingNotesId = tradingNotesId;
        this.file = file;
        this.type = type;
    }

    public WorkOrderTradingNoteAttachmentForm() {
    }

    public long getTradingNotesId() {
        return tradingNotesId;
    }

    public void setTradingNotesId(long tradingNotesId) {
        this.tradingNotesId = tradingNotesId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
