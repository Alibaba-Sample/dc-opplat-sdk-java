/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.common;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;

/**
 * 签名校验
 */
public interface SignChecker {
    boolean check(String sourceContent, String signature, String signType, String charset) throws OpplatApiException;

    boolean checkCert(String sourceContent, String signature, String signType, String charset, String publicKey) throws OpplatApiException;
}