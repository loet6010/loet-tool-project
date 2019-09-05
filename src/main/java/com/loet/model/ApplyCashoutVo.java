package com.loet.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 申请提现VO
 */
public class ApplyCashoutVo implements Serializable {
    private static final long serialVersionUID = 4995081928381454554L;

    /**
     * 提现合作公司商户号
     */
    private String cashoutPartnerCode;

    /**
     * 合作公司订单号
     */
    private String partnerOrderId;

    /**
     * 提现用户身份证号
     */
    private String idCard;

    /**
     * 提现用户手机号
     */
    private String phone;

    /**
     * 提现金额：分
     */
    private Long cash;

    /**
     * unix时间戳
     */
    private Long unixTime;

    /**
     * 签名字符串
     */
    private String sign;

    public String getCashoutPartnerCode() {
        return cashoutPartnerCode;
    }

    public void setCashoutPartnerCode(String cashoutPartnerCode) {
        this.cashoutPartnerCode = cashoutPartnerCode;
    }

    public String getPartnerOrderId() {
        return partnerOrderId;
    }

    public void setPartnerOrderId(String partnerOrderId) {
        this.partnerOrderId = partnerOrderId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCash() {
        return cash;
    }

    public void setCash(Long cash) {
        this.cash = cash;
    }

    public Long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(Long unixTime) {
        this.unixTime = unixTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
