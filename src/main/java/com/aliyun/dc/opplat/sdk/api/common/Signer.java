/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.common;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;

/**
 * 签名基础接口
 */
public interface Signer {

    String sign(String sourceContent, String signType, String charset) throws OpplatApiException;
}