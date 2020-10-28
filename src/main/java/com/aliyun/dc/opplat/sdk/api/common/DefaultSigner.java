/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.common;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.utils.OpenApiSignature;

/**
 * 默认签名器
 */
public class DefaultSigner implements Signer {

    private String privateKey;

    public DefaultSigner(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String sign(String sourceContent, String signType, String charset) throws OpplatApiException {

        return OpenApiSignature.rsaSign(sourceContent, this.privateKey, charset, signType);

    }

    /**
     * Getter method for property <tt>privateKey</tt>.
     *
     * @return property value of privateKey
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Setter method for property <tt>privateKey</tt>.
     *
     * @param privateKey value to be assigned to property privateKey
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}