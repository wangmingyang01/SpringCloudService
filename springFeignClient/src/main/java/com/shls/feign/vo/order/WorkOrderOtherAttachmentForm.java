package com.shls.feign.vo.order;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

public class WorkOrderOtherAttachmentForm {
    @ApiModelProperty("附件")
    private MultipartFile file;

    @ApiModelProperty("附件类型")
    private String type;

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
