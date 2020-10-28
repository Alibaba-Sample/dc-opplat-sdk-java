/**
 * Alipay.com Inc. Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import com.aliyun.dc.opplat.sdk.api.common.SignChecker;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.utils.OpenApiSignature;

/**
 * 签名验证器
 *
 * @author changlei.qcl
 * @version $Id: DefaultSignChecker.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public class DefaultSignChecker implements SignChecker {

    private String platPublicKey;

    public DefaultSignChecker(String platPublicKey) {
        this.platPublicKey = platPublicKey;
    }

    @Override
    public boolean check(String sourceContent, String signature, String signType, String charset) throws OpplatApiException {
        boolean success = false;
        try {
            success = OpenApiSignature.rsaCheck(sourceContent, signature, platPublicKey, charset, signType);
        } catch (OpplatApiException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    @Override
    public boolean checkCert(String sourceContent, String signature, String signType, String charset, String publicKey)
            throws OpplatApiException {
        boolean success = false;
        try {
            success = OpenApiSignature.rsaCheck(sourceContent, signature, publicKey, charset, signType);
        } catch (OpplatApiException e) {
            throw new RuntimeException(e);
        }
        return success;
    }

    /**
     * Getter method for property <tt>platPublicKey</tt>.
     *
     * @return property value of platPublicKey
     */
    public String getPlatPublicKey() {
        return platPublicKey;
    }

    /**
     * Setter method for property <tt>platPublicKey</tt>.
     *
     * @param platPublicKey value to be assigned to property platPublicKey
     */
    public void setPlatPublicKey(String platPublicKey) {
        this.platPublicKey = platPublicKey;
    }
}