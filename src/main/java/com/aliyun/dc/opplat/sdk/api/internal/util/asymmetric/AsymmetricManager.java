/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.util.asymmetric;

import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;

/**
 * 非对称加密算法管理类
 *
 * @author changlei.qcl
 * @version $Id: AsymmetricManager.java, v 0.1 2020年07月27日 8:27 PM changlei.qcl Exp $
 */
public class AsymmetricManager {

    public static IAsymmetricEncryptor getByName(String type) throws OpplatApiException {
        if (OpplatConstants.SIGN_TYPE_RSA.equals(type)) {
            return new RSAEncryptor();
        }
        if (OpplatConstants.SIGN_TYPE_RSA2.equals(type)) {
            return new RSA2Encryptor();
        }
        throw new OpplatApiException("无效的非对称加密类型:[\" + type + \"]，可选值为：RSA、RSA2");
    }
}