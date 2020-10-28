/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request.dataopen;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;

/**
 * 排序模型
 *
 * @author changlei.qcl
 * @version $Id: OrderParamModel.java, v 0.1 2020年08月05日 11:39 AM changlei.qcl Exp $
 */
public class OrderParamModel extends OpplatObject {

    private static final long serialVersionUID = -7424960764457549369L;

    /**
     * ASC，DESC
     */
    @ApiField("op")
    private String op;

    @ApiField("name")
    private String name;

    @ApiField("priority")
    private Integer priority;

    public OrderParamModel(String name, String op, Integer priority) {
        this.name = name;
        this.op = op;
        this.priority = priority;
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
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>priority</tt>.
     *
     * @return property value of priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * Setter method for property <tt>priority</tt>.
     *
     * @param priority value to be assigned to property priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}