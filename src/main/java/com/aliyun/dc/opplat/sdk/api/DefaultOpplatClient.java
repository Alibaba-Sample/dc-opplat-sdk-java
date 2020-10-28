/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import com.aliyun.dc.opplat.sdk.api.common.DefaultSigner;
import com.aliyun.dc.opplat.sdk.api.common.SignChecker;
import com.aliyun.dc.opplat.sdk.api.common.Signer;

/**
 * 默认的平台客户端
 *
 * @author changlei.qcl
 * @version $Id: DefaultOpplatClient.java, v 0.1 2020年07月28日 2:38 PM changlei.qcl Exp $
 */
public class DefaultOpplatClient extends AbstractClient {

    /**
     * 应用的私钥，签名使用
     */
    private String privateKey;

    /**
     * 平台的公钥，验签使用
     */
    private String platPublicKey;

    /**
     * 签名器，拿应用私钥构成
     */
    private Signer      signer;
    private SignChecker signChecker;

    public DefaultOpplatClient(String serverUrl, String appId, String privateKey, String format,
                               String charset, String platPublicKey, String signType) {
        super(serverUrl, appId, format, charset, signType);
        this.privateKey = privateKey;
        this.signer = new DefaultSigner(privateKey);
        this.platPublicKey = platPublicKey;
        this.signChecker = new DefaultSignChecker(platPublicKey);
    }

    public DefaultOpplatClient(String serverUrl, String appId, String privateKey) {
        super(serverUrl, appId, null, null, null);
        this.privateKey = privateKey;
        this.signer = new DefaultSigner(privateKey);
    }

    @Override
    public SignChecker getSignChecker() {
        return signChecker;
    }

    @Override
    public Signer getSigner() {
        return signer;
    }

    public static class Builder {

        private DefaultOpplatClient client;

        Builder(String serverUrl, String appId, String privateKey) {
            client = new DefaultOpplatClient(serverUrl, appId, privateKey);
        }

        public DefaultOpplatClient build() {
            return client;
        }

        public Builder format(String format) {
            client.setFormat(format);
            return this;
        }

        public Builder signType(String signType) {
            client.setSignType(signType);
            return this;
        }

        public Builder charset(String charset) {
            client.setCharset(charset);
            return this;
        }

        public Builder platPublicKey(String platPublicKey) {
            client.setPlatPublicKey(platPublicKey);
            return this;
        }

        public Builder connectTimeout(int connectTimeout) {
            client.setConnectTimeout(connectTimeout);
            return this;
        }

        public Builder readTimeout(int readTimeout) {
            client.setReadTimeout(readTimeout);
            return this;
        }
    }

    /**
     * Setter method for property <tt>privateKey</tt>.
     *
     * @param privateKey value to be assigned to property privateKey
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        if (this.signer == null) {
            this.signer = new DefaultSigner(privateKey);
        }
    }

    /**
     * Setter method for property <tt>platPublicKey</tt>.
     *
     * @param platPublicKey value to be assigned to property platPublicKey
     */
    public void setPlatPublicKey(String platPublicKey) {
        this.platPublicKey = platPublicKey;
        if (this.signChecker == null) {
            this.signChecker = new DefaultSignChecker(platPublicKey);
        }
    }

}