/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.util;

import com.aliyun.dc.opplat.sdk.api.common.DcLabHashMap;

/**
 * OpenApi上下文
 *
 * @author changlei.qcl
 * @version $Id: OpenApiContext.java, v 0.1 2020年07月27日 4:13 PM changlei.qcl Exp $
 */
public class RequestParametersHolder {

    /**
     * 协议必须字段
     */
    private DcLabHashMap protocalMustParams;

    /**
     * 协议可选字段
     */
    private DcLabHashMap protocalOptParams;

    /**
     * 业务参数字段
     */
    private DcLabHashMap applicationParams;

    /**
     * 业务字段，不参与验签
     */
    private DcLabHashMap bizParams;

    /**
     * Getter method for property <tt>protocalMustParams</tt>.
     *
     * @return property value of protocalMustParams
     */
    public DcLabHashMap getProtocalMustParams() {
        return protocalMustParams;
    }

    /**
     * Setter method for property <tt>protocalMustParams</tt>.
     *
     * @param protocalMustParams value to be assigned to property protocalMustParams
     */
    public void setProtocalMustParams(DcLabHashMap protocalMustParams) {
        this.protocalMustParams = protocalMustParams;
    }

    /**
     * Getter method for property <tt>protocalOptParams</tt>.
     *
     * @return property value of protocalOptParams
     */
    public DcLabHashMap getProtocalOptParams() {
        return protocalOptParams;
    }

    /**
     * Setter method for property <tt>protocalOptParams</tt>.
     *
     * @param protocalOptParams value to be assigned to property protocalOptParams
     */
    public void setProtocalOptParams(DcLabHashMap protocalOptParams) {
        this.protocalOptParams = protocalOptParams;
    }

    /**
     * Getter method for property <tt>applicationParams</tt>.
     *
     * @return property value of applicationParams
     */
    public DcLabHashMap getApplicationParams() {
        return applicationParams;
    }

    /**
     * Setter method for property <tt>applicationParams</tt>.
     *
     * @param applicationParams value to be assigned to property applicationParams
     */
    public void setApplicationParams(DcLabHashMap applicationParams) {
        this.applicationParams = applicationParams;
    }

    /**
     * Getter method for property <tt>bizParams</tt>.
     *
     * @return property value of bizParams
     */
    public DcLabHashMap getBizParams() {
        return bizParams;
    }

    /**
     * Setter method for property <tt>bizParams</tt>.
     *
     * @param bizParams value to be assigned to property bizParams
     */
    public void setBizParams(DcLabHashMap bizParams) {
        this.bizParams = bizParams;
    }
}