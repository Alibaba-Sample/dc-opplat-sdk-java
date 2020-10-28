/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.common;

/**
 * 常量
 *
 * @author changlei.qcl
 * @version $Id: CommonConstants.java, v 0.1 2020年07月28日 2:44 PM changlei.qcl Exp $
 */
public class OpplatConstants {

    /**
     * sha256WithRsa 算法请求类型
     */
    public static final String SIGN_TYPE_RSA2 = "RSA2";

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String SIGN_SHA256RSA_ALGORITHMS = "SHA256WithRSA";

    public static final String SIGN_TYPE_RSA = "RSA";

    /**
     * JSON 应格式
     */
    public static final String FORMAT_JSON = "json";

    /**
     * XML 应格式
     */
    public static final String FORMAT_XML = "xml";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIMEZONE = "GMT+8";

    /**
     * UTF-8字符集
     **/
    public static final String CHARSET_UTF8 = "UTF-8";

    /**
     * 新版本节点后缀
     */
    public static final String RESPONSE_SUFFIX = "_response";

    public static final String SIGN = "sign";

    public static final String BIZ_CONTENT_KEY = "biz_content";

    /**
     * 分配给使用方的应用ID
     */
    public static final String API_REQ_APP_ID = "app_id";

    /**
     * 接口名称
     */
    public static final String API_REQ_FUNC = "function";

    /**
     * 版本号
     */
    public static final String API_REQ_VERSION = "version";

    /**
     * 格式：仅支持JSON
     */
    public static final String API_REQ_FORMAT = "format";

    /**
     * 请求格式使用的编码，utf-8
     */
    public static final String API_REQ_CHARSET = "charset";

    /**
     * 签名类型
     */
    public static final String API_REQ_SIGN_TYPE = "sign_type";

    /**
     * 发送请求的时间，不带时区，格式 "yyyy-MM-dd HH:mm:ss"
     */
    public static final String API_REQ_TIME = "reqTime";

}