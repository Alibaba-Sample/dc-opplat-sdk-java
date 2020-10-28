/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;

/**
 * 开放平台的Client端
 *
 * @author changlei.qcl
 * @version $Id: OpOplatClient.java, v 0.1 2020年07月28日 2:26 PM changlei.qcl Exp $
 */
public interface OpplatClient {

    /**
     * @param <T>
     * @param request
     * @return
     * @throws OpplatApiException
     */
    <T extends BaseOpplatResponse> T execute(OpplatRequest<T> request) throws OpplatApiException;
}