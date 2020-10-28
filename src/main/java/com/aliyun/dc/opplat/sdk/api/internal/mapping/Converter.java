package com.aliyun.dc.opplat.sdk.api.internal.mapping;

import com.aliyun.dc.opplat.sdk.api.SignItem;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;

/**
 * JSON格式转换器。
 *
 * @author changlei.qcl
 * @version $Id: Converter.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public interface Converter {

    /**
     * 把字符串转换为响应对象。
     *
     * @param <T>   领域泛型
     * @param rsp   响应字符串
     * @param clazz 领域类型
     * @return 响应对象
     * @throws OpplatApiException
     */
    <T extends BaseOpplatResponse> T toResponse(String rsp, Class<T> clazz) throws OpplatApiException;

    /**
     * 获取响应内的签名数据
     *
     * @param request
     * @param responseBody
     * @return
     * @throws OpplatApiException
     */
    SignItem getSignItem(OpplatRequest<?> request, String responseBody)
            throws OpplatApiException;

}
