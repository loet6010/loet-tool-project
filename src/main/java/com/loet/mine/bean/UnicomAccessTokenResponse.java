package com.loet.mine.bean;

import java.io.Serializable;

/**
 * 描述：UnicomAccessTokenResponse
 *
 * @author 罗锅
 * @date 2021/10/26 15:58
 */
public class UnicomAccessTokenResponse implements Serializable {
    private static final long serialVersionUID = -6381762659189715154L;

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
