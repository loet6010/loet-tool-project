package com.loet.mine.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 描述：CspGetFileRequest
 *
 * @author 罗锅
 * @date 2021/10/27 9:50
 */
public class CspGetFileRequest {

    private String fileUrl;

    @JSONField(serialize = false)
    private String fileName;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
