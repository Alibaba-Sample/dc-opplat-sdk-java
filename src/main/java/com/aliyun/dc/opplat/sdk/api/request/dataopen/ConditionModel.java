/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request.dataopen;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;

/**
 * 查询条件
 *
 * @author changlei.qcl
 * @version $Id: ConditionModel.java, v 0.1 2020年08月05日 11:39 AM changlei.qcl Exp $
 */
public class ConditionModel extends OpplatObject {

    private static final long serialVersionUID = 6112814227713718785L;

    /**
     * <p>  "lt"-"小于 <" </p>
     * <p>  "le"-"小于等于 <=" </p>
     * <p>  "gt"-"大于 >" </p>
     * <p>  "ge"-"大于等于 >=" </p>
     * <p>  "eq"-"等于" </p>
     * <p>  "neq"-"不等于" </p>
     * <p>  "in"-"包含" </p>
     * <p>  "not_in"-"不包含"</p>
     * <p>  "is_null"-"为空"</p>
     * <p>  "not_null"-"不为空"</p>
     */
    @ApiField("op")
    private String op;

    @ApiField("value")
    private String value;

    public ConditionModel(String op, String value) {
        this.op = op;
        this.value = value;
    }

    /**
     * Getter method for property <tt>op</tt>.
     *
     * @return property value of op
     */
    public String getOp() {
        return op;
    }

    /**
     * Setter method for property <tt>op</tt>.
     *
     * @param op value to be assigned to property op
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */
    public String getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */
    public void setValue(String value) {
        this.value = value;
    }
}