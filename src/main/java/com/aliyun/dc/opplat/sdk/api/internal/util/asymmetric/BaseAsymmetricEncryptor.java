/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.util.asymmetric;

import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.utils.StringUtils;

/**
 * @author changlei.qcl
 * @version $Id: BaseAsymmetricEncryptor.java, v 0.1 2020年07月27日 8:31 PM changlei.qcl Exp $
 */
public abstract class BaseAsymmetricEncryptor implements IAsymmetricEncryptor {

    //默认字符集编码。现在推荐使用UTF-8
    private static String DEFAULT_CHARSET = "UTF-8";

    @Override
    public String decrypt(String cipherTextBase64, String charset, String privateKey) throws OpplatApiException {
        try {
            if (StringUtils.isEmpty(cipherTextBase64)) {
                throw new OpplatApiException("密文不可为空");
            }
            if (StringUtils.isEmpty(privateKey)) {
                throw new OpplatApiException("私钥不可为空");
            }
            if (StringUtils.isEmpty(charset)) {
                charset = DEFAULT_CHARSET;
            }
            return doDecrypt(cipherTextBase64, charset, privateKey);

        } catch (Exception e) {

            String errorMessage = getAsymmetricType() + "非对称解密遭遇异常，请检查私钥格式是否正确。" + e.getMessage() +
                    " cipherTextBase64=" + cipherTextBase64 + "，charset=" + charset + "，privateKeySize=" + privateKey.length();
            throw new OpplatApiException(errorMessage, e);
        }

    }

    @Override
    public String encrypt(String plainText, String charset, String publicKey) throws OpplatApiException {
        try {
            if (StringUtils.isEmpty(plainText)) {
                throw new OpplatApiException("密文不可为空");
            }
            if (StringUtils.isEmpty(publicKey)) {
                throw new OpplatApiException("公钥不可为空");
            }
            if (StringUtils.isEmpty(charset)) {
                charset = DEFAULT_CHARSET;
            }
            return doEncrypt(plainText, charset, publicKey);
        } catch (Exception e) {

            String errorMessage = getAsymmetricType() + "非对称解密遭遇异常，请检查公钥格式是否正确。" + e.getMessage() +
                    " plainText=" + plainText + "，charset=" + charset + "，publicKey=" + publicKey;
            throw new OpplatApiException(errorMessage, e);
        }

    }

    @Override
    public String sign(String content, String charset, String privateKey) throws OpplatApiException {
        try {
            if (StringUtils.isEmpty(content)) {
                throw new OpplatApiException("待签名内容不可为空");
            }
            if (StringUtils.isEmpty(privateKey)) {
                throw new OpplatApiException("私钥不可为空");
            }
            if (StringUtils.isEmpty(charset)) {
                charset = DEFAULT_CHARSET;
            }
            return doSign(content, charset, privateKey);
        } catch (Exception e) {

            String errorMessage = getAsymmetricType() + "签名遭遇异常，请检查私钥格式是否正确。" + e.getMessage() +
                    " content=" + content + "，charset=" + charset + "，privateKeySize=" + privateKey.length();
            throw new OpplatApiException(errorMessage, e);
        }

    }

    @Override
    public boolean verify(String content, String charset, String publicKey, String sign) throws OpplatApiException {
        try {
            if (StringUtils.isEmpty(content)) {
                throw new OpplatApiException("待验签内容不可为空");
            }
            if (StringUtils.isEmpty(publicKey)) {
                throw new OpplatApiException("公钥不可为空");
            }
            if (StringUtils.isEmpty(sign)) {
                throw new OpplatApiException("签名串不可为空");
            }
            if (StringUtils.isEmpty(charset)) {
                charset = DEFAULT_CHARSET;
            }
            return doVerify(content, charset, publicKey, sign);
        } catch (Exception e) {

            String errorMessage = getAsymmetricType() + "验签遭遇异常，请检查公钥格式是否正确。" + e.getMessage() +
                    " content=" + content + "，charset=" + charset + "，publicKey=" + publicKey;
            throw new OpplatApiException(errorMessage, e);
        }
    }

    protected abstract String doDecrypt(String cipherTextBase64, String charset, String privateKey) throws Exception;

    protected abstract String doEncrypt(String plainText, String charset, String publicKey) throws Exception;

    protected abstract String doSign(String content, String charset, String privateKey) throws Exception;

    protected abstract boolean doVerify(String content, String charset, String publicKey, String sign) throws Exception;

    protected abstract String getAsymmetricType();
}