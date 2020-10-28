/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.response;

import java.io.Serializable;
import java.util.Map;

import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;

/**
 * API基础响应信息。
 *
 * @author changlei.qcl
 * @version $Id: OpOplatResponse.java, v 0.1 2020年07月28日 2:27 PM changlei.qcl Exp $
 */
public abstract class BaseOpplatResponse implements Serializable {

    private static final long serialVersionUID = -328991347817116476L;

    @ApiField("code")
    private String code;

    @ApiField("msg")
    private String msg;

    @ApiField("status")
    private String status;

    private Map<String, String> params;

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>msg</tt>.
     *
     * @return property value of msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter method for property <tt>msg</tt>.
     *
     * @param msg value to be assigned to property msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter method for property <tt>body</tt>.
     *
     * @return property value of status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>body</tt>.
     *
     * @param status value to be assigned to property status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>params</tt>.
     *
     * @return property value of params
     */
    public Map<String, String> getParams() {
        return params;
    }

    /**
     * Setter method for property <tt>params</tt>.
     *
     * @param params value to be assigned to property params
     */
    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**
     * 结果是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return "0000".equals(code);
    }

    @Override
    public String toString() {
        return "BaseOpplatResponse(" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", status='" + status + '\'' +
                ", params=" + params +
                ")";
    }

}