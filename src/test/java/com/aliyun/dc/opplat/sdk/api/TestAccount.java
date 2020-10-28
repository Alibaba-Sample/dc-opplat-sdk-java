/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.dc.opplat.sdk.api.internal.util.file.IOUtils;

/**
 * @author changlei.qcl
 * @version $Id: TestAccount.java, v 0.1 2020年07月29日 10:47 AM changlei.qcl Exp $
 */
public class TestAccount {

    public static class LOCAL {

        public static final String GATEWAYT_URL = "http://127.0.0.1:7001/op/gw/gateway.do";

        public static final String PLAT_PUBLICKEY
                =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmXrheXh9fjIY8FdRwfvCHKoORNnUIadONRNa50y9Av9OueFt5WRU2N4lq40uk2efDgfeNPSopyNxbi0KUI7l0zG073RPRsmzdSJ9dfV9oz3dJJIIjtfs8f+0ufoQYmJ708HiC6sKhJj+wUSXhFyKZDZItOM+9FD74NADZz1exNWuzulw9ovS9mYIIaO9r+MAtdlUOJfZjz/UqPUSldnJV+OBnakg3OYqZxkU6M6P9h1Enm6l5AfEoDH5v4FyCeymbkexjTeZ7268muNMPQOMvDqvjG+lNMVBJgsbiS64+99yVAJRzgwVom0H03Zu4JvxbkhNHcYnLfH4roJu/2EcqwIDAQAB";

        public static final String APP_ID = "7998749925749358592";

        //public static final String API_CODE = "ec_dc_api_1231596524840019";
        public static final String API_CODE = "assert_info";

        public static final String API_VERSION = "1.0.2";

        public static final String APP_PRIVATE_KEY = getPrivateKey("LOCAL");

    }

    /**
     * 从文件中读取私钥
     * <p>
     * 注意：实际开发过程中，请务必注意不要将私钥信息配置在源码中（比如配置为常量或储存在配置文件的某个字段中等），因为私钥的保密等级往往比源码高得多，将会增加私钥泄露的风险。推荐将私钥信息储存在专用的私钥文件中，
     * 将私钥文件通过安全的流程分发到服务器的安全储存区域上，仅供自己的应用运行时读取。
     * <p>
     * 此处为了单元测试执行的环境普适性，私钥文件配置在resources资源下，实际过程中请不要这样做。
     *
     * @param key 私钥对应的Key
     * @return 私钥字符串
     */
    private static String getPrivateKey(String key) {
        try {
            InputStream stream = TestAccount.class.getResourceAsStream("/fixture/privateKey.json");
            JSONObject jsonObject = JSON.parseObject(IOUtils.toString(stream, "utf-8"));
            return jsonObject.getString(key);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}