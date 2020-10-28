/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request;

import java.util.Map;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.common.DcLabHashMap;
import com.aliyun.dc.opplat.sdk.api.response.DateOpenQueryResponse;

/**
 * 数据开放的平台查询请求
 *
 * @author changlei.qcl
 * @version $Id: DateOpenQueryRequest.java, v 0.1 2020年07月29日 11:05 AM changlei.qcl Exp $
 */
public class DateOpenQueryRequest implements OpplatRequest<DateOpenQueryResponse> {
    /**
     * API名称
     */
    private String apiMethodName;

    /**
     * API版本
     */
    private String apiVersion;

    /**
     * 自定义参数 add user-defined text parameters
     * <p>暂未启用</p>
     */
    private DcLabHashMap udfParams;

    /**
     * 业务参数结构体，JSON的字符串格式。bizContent 和 bizModel 不能同时设置
     */
    private String bizContent;

    /**
     * 强模型的对象，bizContent 和 bizModel 不能同时设置
     */
    private OpplatObject bizModel = null;

    public DateOpenQueryRequest(String apiMethodName, String apiVersion) {
        this.apiMethodName = apiMethodName;
        this.apiVersion = apiVersion;
    }

    @Override
    public String getApiMethodName() {
        return this.apiMethodName;
    }

    @Override
    public String getApiVersion() {
        return this.apiVersion;
    }

    @Override
    public Class<DateOpenQueryResponse> getResponseClass() {
        return DateOpenQueryResponse.class;
    }

    @Override
    public Map<String, String> getTextParams() {
        DcLabHashMap txtParams = new DcLabHashMap();
        txtParams.put("biz_content", this.bizContent);
        if (udfParams != null) {
            txtParams.putAll(this.udfParams);
        }
        return txtParams;
    }

    @Override
    public OpplatObject getBizModel() {
        return this.bizModel;
    }

    @Override
    public void setBizModel(OpplatObject bizModel) {
        this.bizModel = bizModel;
    }

    /**
     * 设置其他文本参数
     *
     * @param key
     * @param value
     */
    public void putOtherTextParam(String key, String value) {
        if (this.udfParams == null) {
            this.udfParams = new DcLabHashMap();
        }
        this.udfParams.put(key, value);
    }

    /**
     * Getter method for property <tt>bizContent</tt>.
     *
     * @return property value of bizContent
     */
    public String getBizContent() {
        return bizContent;
    }

    /**
     * Setter method for property <tt>bizContent</tt>.
     *
     * @param bizContent value to be assigned to property bizContent
     */
    public void setBizContent(String bizContent) {
        this.bizContent = bizContent;
    }

}