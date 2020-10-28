/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request.dataopen;

import java.util.ArrayList;
import java.util.List;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiListField;

/**
 * 数据开放平台查询模型
 *
 * @author changlei.qcl
 * @version $Id: QueryConditionModel.java, v 0.1 2020年08月05日 11:38 AM changlei.qcl Exp $
 */
public class QueryConditionModel extends OpplatObject {

    private static final long serialVersionUID = 2851773602774142844L;

    @ApiField("name")
    private String name;

    @ApiListField("condition_infos")
    private List<ConditionModel> conditionInfoList;

    public QueryConditionModel(String name) {
        this.name = name;
    }

    /**
     * 添加查询模型
     *
     * @param conditionModel
     */
    public void addConditionModel(ConditionModel conditionModel) {
        if (null == this.conditionInfoList) {
            this.conditionInfoList = new ArrayList<>();
        }
        this.conditionInfoList.add(conditionModel);
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
     * Getter method for property <tt>conditionInfoList</tt>.
     *
     * @return property value of conditionInfoList
     */
    public List<ConditionModel> getConditionInfoList() {
        return conditionInfoList;
    }

    /**
     * Setter method for property <tt>conditionInfoList</tt>.
     *
     * @param conditionInfoList value to be assigned to property conditionInfoList
     */
    public void setConditionInfoList(List<ConditionModel> conditionInfoList) {
        this.conditionInfoList = conditionInfoList;
    }
}