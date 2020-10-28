/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.parser.json;

import com.aliyun.dc.opplat.sdk.api.OpplatParser;
import com.aliyun.dc.opplat.sdk.api.SignItem;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converter;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;

/**
 * 单个JSON对象解释器。
 *
 * @author changlei.qcl
 * @version $Id: ObjectJsonParser.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public class ObjectJsonParser<T extends BaseOpplatResponse> implements OpplatParser<T> {

    private Class<T> clazz;

    public ObjectJsonParser(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T parse(String rsp) throws OpplatApiException {
        Converter converter = new JsonConverter();
        return converter.toResponse(rsp, clazz);
    }

    @Override
    public Class<T> getResponseClass() throws OpplatApiException {
        return clazz;
    }

    @Override
    public SignItem getSignItem(OpplatRequest<?> request, String responseBody) throws OpplatApiException {
        Converter converter = new JsonConverter();
        return converter.getSignItem(request, responseBody);
    }
}