/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.exception;

/**
 * @author changlei.qcl
 * @version $Id: OpplatApiException.java, v 0.1 2020年07月28日 2:33 PM changlei.qcl Exp $
 */
public class OpplatApiException extends Exception {

    private static final long serialVersionUID = 8571085664943974367L;

    private String errCode;
    private String errMsg;

    public OpplatApiException() {
        super();
    }

    public OpplatApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpplatApiException(String message) {
        super(message);
    }

    public OpplatApiException(Throwable cause) {
        super(cause);
    }

    public OpplatApiException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

}