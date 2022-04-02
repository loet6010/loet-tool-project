package com.loet.mine.bean;

import java.io.Serializable;

/**
 * 描述：UnicomResult
 *
 * @author 罗锅
 * @date 2021/10/26 15:51
 */
public class UnicomResult<T> implements Serializable {
    private static final long serialVersionUID = -6673020277294411844L;

    private Integer code;

    private String message;

    private T data;

    /**
     * 返回请求是否成功
     *
     * @return
     */
    public boolean success() {
        return UnicomConstants.SUCCESS.equals(this.code);
    }

    /**
     * 返回请求是否失败
     *
     * @return
     */
    public boolean failed() {
        return !success();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
