/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request;

import java.util.Map;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;

/**
 * 请求接口
 *
 * @author changlei.qcl
 * @version $Id: OpOplatRequest.java, v 0.1 2020年07月28日 2:27 PM changlei.qcl Exp $
 */
public interface OpplatRequest<T extends BaseOpplatResponse> {

    /**
     * 获取API名称。
     *
     * @return API名称
     */
    String getApiMethodName();

    /**
     * 得到当前接口的版本
     *
     * @return API版本
     */
    String getApiVersion();

    /**
     * 得到当前API的响应结果类型
     *
     * @return 响应类型
     */
    Class<T> getResponseClass();

    /**
     * 获取所有的Key-Value形式的文本请求参数集合。其中：
     * <ul>
     * <li>Key: 请求参数名</li>
     * <li>Value: 请求参数值</li>
     * </ul>
     *
     * @return 文本请求参数集合
     */
    Map<String, String> getTextParams();

    /**
     * 获取业务实体
     *
     * @return
     */
    OpplatObject getBizModel();

    /**
     * 设置业务实体，如需使用此方法，请勿直接设置biz_content
     *
     * @param bizModel
     */
    void setBizModel(OpplatObject bizModel);

}