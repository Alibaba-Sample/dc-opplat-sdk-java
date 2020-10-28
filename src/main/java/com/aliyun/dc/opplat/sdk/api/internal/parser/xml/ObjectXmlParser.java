package com.aliyun.dc.opplat.sdk.api.internal.parser.xml;

import com.aliyun.dc.opplat.sdk.api.OpplatParser;
import com.aliyun.dc.opplat.sdk.api.SignItem;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converter;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;


/**
 * 单个XML对象解释器。
 *
 * @author carver.gu
 * @since 1.0, Apr 11, 2010
 */
public class ObjectXmlParser<T extends BaseOpplatResponse> implements OpplatParser<T> {

    private Class<T> clazz;

    public ObjectXmlParser(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T parse(String rsp) throws OpplatApiException {
        Converter converter = new XmlConverter();
        return converter.toResponse(rsp, clazz);
    }


    @Override
    public Class<T> getResponseClass() {
        return clazz;
    }

    @Override
    public SignItem getSignItem(OpplatRequest<?> request, String responseBody)
            throws OpplatApiException {

        Converter converter = new XmlConverter();

        return converter.getSignItem(request, responseBody);
    }

}
