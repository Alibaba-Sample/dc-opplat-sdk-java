/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import java.io.Serializable;

/**
 * 签名类型
 *
 * @author changlei.qcl
 * @version $Id: SignItem.java, v 0.1 2020年07月28日 9:11 PM changlei.qcl Exp $
 */
public class SignItem implements Serializable {

    private static final long serialVersionUID = -7388592201934097218L;

    /**
     * 签名源串
     */
    private String signSourceDate;

    /**
     * 签名
     */
    private String sign;

    /**
     * Getter method for property <tt>signSourceDate</tt>.
     *
     * @return property value of signSourceDate
     */
    public String getSignSourceDate() {
        return signSourceDate;
    }

    /**
     * Setter method for property <tt>signSourceDate</tt>.
     *
     * @param signSourceDate value to be assigned to property signSourceDate
     */
    public void setSignSourceDate(String signSourceDate) {
        this.signSourceDate = signSourceDate;
    }

    /**
     * Getter method for property <tt>sign</tt>.
     *
     * @return property value of sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * Setter method for property <tt>sign</tt>.
     *
     * @param sign value to be assigned to property sign
     */
    public void setSign(String sign) {
        this.sign = sign;
    }
}