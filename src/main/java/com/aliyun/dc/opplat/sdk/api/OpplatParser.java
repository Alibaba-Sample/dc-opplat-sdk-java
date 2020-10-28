/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;

/**
 * 平台解析器
 * @author changlei.qcl
 * @version $Id: OpplatParser.java, v 0.1 2020年07月28日 9:10 PM changlei.qcl Exp $
 */
public interface OpplatParser<T extends BaseOpplatResponse> {

    /**
     * 把响应字符串解释成相应的领域对象。
     *
     * @param rsp 响应字符串
     * @return 领域对象
     */
    T parse(String rsp) throws OpplatApiException;

    /**
     * 获取响应类类型。
     */
    Class<T> getResponseClass() throws OpplatApiException;

    /**
     * 获取响应内的签名数据
     *
     * @param responseBody 响应字符串
     * @return
     * @throws OpplatApiException
     */
    SignItem getSignItem(OpplatRequest<?> request, String responseBody) throws OpplatApiException;

}