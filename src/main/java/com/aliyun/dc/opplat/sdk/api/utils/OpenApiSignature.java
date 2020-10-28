/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.aliyun.dc.opplat.sdk.api.common.DcLabHashMap;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.util.RequestParametersHolder;
import com.aliyun.dc.opplat.sdk.api.internal.util.SignSourceData;
import com.aliyun.dc.opplat.sdk.api.internal.util.asymmetric.AsymmetricManager;

/**
 * 签名工具
 *
 * @author changlei.qcl
 * @version $Id: OpenApiSignature.java, v 0.1 2020年07月27日 6:04 PM changlei.qcl Exp $
 */
public class OpenApiSignature {

    /**
     * 获取签名方法
     *
     * @param requestHolder
     * @return
     */
    public static String getSignatureContent(RequestParametersHolder requestHolder) {
        return getSignContent(getSortedMap(requestHolder));
    }

    /**
     * 密钥模式RSA、RSA2、SM2通用验签方法
     *
     * @param requestHolder 待验签收到的请求参数列表
     * @param publicKey     数据中台公钥
     * @param charset       参数内容编码集
     * @param signType      指定采用的签名方式，RSA、RSA2、SM2
     * @return true：验签通过；false：验签不通过
     */
    public static boolean verifySignatureContent(RequestParametersHolder requestHolder, String publicKey,
                                                 String charset, String signType) throws OpplatApiException {
        Map<String, String> verifyMap = getSortedMap(requestHolder);
        return verifyV1(verifyMap, publicKey, charset, signType);
    }

    /**
     * 通用签名方法
     *
     * @param params     待签名内容
     * @param privateKey 私钥
     * @param charset    编码格式
     * @param signType   签名类型：RSA、RSA2、SM2
     * @return
     */
    public static String sign(Map<String, String> params, String privateKey, String charset,
                              String signType) throws OpplatApiException {
        String signContent = getSignContent(params);
        return AsymmetricManager.getByName(signType).sign(signContent, charset, privateKey);
    }

    /**
     * 通用签名方法
     *
     * @param signContent 待签名内容
     * @param privateKey  私钥
     * @param charset     编码格式
     * @param signType    签名类型：RSA、RSA2、SM2
     * @return
     */
    public static String sign(String signContent, String privateKey, String charset,
                              String signType) throws OpplatApiException {
        return AsymmetricManager.getByName(signType).sign(signContent, charset, privateKey);
    }

    /**
     * 密钥模式RSA、RSA2、SM2通用验签方法
     *
     * @param params    待验签的参数Map
     * @param publicKey 数据中台公钥
     * @param charset   参数内容编码集
     * @param signType  指定采用的签名方式，RSA、RSA2、SM2
     * @return true：验签通过；false：验签不通过
     */
    public static boolean verifyV1(Map<String, String> params, String publicKey,
                                   String charset, String signType) throws OpplatApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);
        // 待打印验证签名的字符串
        return AsymmetricManager.getByName(signType).verify(content, charset, publicKey, sign);
    }

    /**
     * 获取要验签的字符串
     *
     * @param params
     * @return
     */
    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    /**
     * 获取签名字符串
     *
     * @param sortedParams 排序好的参数列表
     * @return
     */
    public static String getSignContent(Map<String, String> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<String>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;
        for (String key : keys) {
            String value = sortedParams.get(key);
            if (StringUtils.areNotEmpty(key, value)) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                index++;
            }
        }
        return content.toString();
    }

    /**
     * 将请求参数进行排序
     *
     * @param requestHolder
     * @return
     */
    public static Map<String, String> getSortedMap(RequestParametersHolder requestHolder) {
        Map<String, String> sortedParams = new TreeMap<String, String>();
        DcLabHashMap appParams = requestHolder.getApplicationParams();

        if (appParams != null && appParams.size() > 0) {
            sortedParams.putAll(appParams);
        }

        DcLabHashMap protocalMustParams = requestHolder.getProtocalMustParams();
        if (protocalMustParams != null && protocalMustParams.size() > 0) {
            sortedParams.putAll(protocalMustParams);
        }

        DcLabHashMap protocalOptParams = requestHolder.getProtocalOptParams();
        if (protocalOptParams != null && protocalOptParams.size() > 0) {
            sortedParams.putAll(protocalOptParams);
        }

        return sortedParams;
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset,
                                   String signType) throws OpplatApiException {

        return AsymmetricManager.getByName(signType).verify(content, charset, publicKey, sign);
    }

    /**
     * sha1WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public static String rsaSign(String content, String privateKey, String charset) throws OpplatApiException {

        return AsymmetricManager.getByName("RSA").sign(content, charset, privateKey);
    }

    /**
     * rsa内容签名
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     */
    public static String rsaSign(String content, String privateKey, String charset, String signType) throws OpplatApiException {

        return AsymmetricManager.getByName(signType).sign(content, charset, privateKey);
    }

    public static SignSourceData extractSignContent(String str, int begin) {
        if (str == null) {
            return null;
        }

        int beginIndex = extractBeginPosition(str, begin);
        if (beginIndex >= str.length()) {
            return null;
        }

        int endIndex = extractEndPosition(str, beginIndex);
        return new SignSourceData(str.substring(beginIndex, endIndex), beginIndex, endIndex);
    }

    private static int extractBeginPosition(String responseString, int begin) {
        int beginPosition = begin;
        //找到第一个左大括号（对应响应的是JSON对象的情况：普通调用OpenAPI响应明文）
        //或者双引号（对应响应的是JSON字符串的情况：加密调用OpenAPI响应Base64串），作为待验签内容的起点
        while (beginPosition < responseString.length()
                && responseString.charAt(beginPosition) != '{'
                && responseString.charAt(beginPosition) != '"') {
            ++beginPosition;
        }
        return beginPosition;
    }

    private static int extractEndPosition(String responseString, int beginPosition) {
        //提取明文验签内容终点
        if (responseString.charAt(beginPosition) == '{') {
            return extractJsonObjectEndPosition(responseString, beginPosition);
        }
        //提取密文验签内容终点
        else {
            return extractJsonBase64ValueEndPosition(responseString, beginPosition);
        }
    }

    private static int extractJsonObjectEndPosition(String responseString, int beginPosition) {
        //记录当前尚未发现配对闭合的大括号
        LinkedList<String> braces = new LinkedList<String>();
        //记录当前字符是否在双引号中
        boolean inQuotes = false;
        //记录当前字符前面连续的转义字符个数
        int consecutiveEscapeCount = 0;
        //从待验签字符的起点开始遍历后续字符串，找出待验签字符串的终止点，终点即是与起点{配对的}
        for (int index = beginPosition; index < responseString.length(); ++index) {
            //提取当前字符
            char currentChar = responseString.charAt(index);

            //如果当前字符是"且前面有偶数个转义标记（0也是偶数）
            if (currentChar == '"' && consecutiveEscapeCount % 2 == 0) {
                //是否在引号中的状态取反
                inQuotes = !inQuotes;
            }
            //如果当前字符是{且不在引号中
            else if (currentChar == '{' && !inQuotes) {
                //将该{加入未闭合括号中
                braces.push("{");
            }
            //如果当前字符是}且不在引号中
            else if (currentChar == '}' && !inQuotes) {
                //弹出一个未闭合括号
                braces.pop();
                //如果弹出后，未闭合括号为空，说明已经找到终点
                if (braces.isEmpty()) {
                    return index + 1;
                }
            }

            //如果当前字符是转义字符
            if (currentChar == '\\') {
                //连续转义字符个数+1
                ++consecutiveEscapeCount;
            } else {
                //连续转义字符个数置0
                consecutiveEscapeCount = 0;
            }
        }

        //如果没有找到配对的闭合括号，说明验签内容片段提取失败，直接尝试选取剩余整个响应字符串进行验签
        return responseString.length();
    }

    private static int extractJsonBase64ValueEndPosition(String responseString, int beginPosition) {
        for (int index = beginPosition; index < responseString.length(); ++index) {
            //找到第2个双引号作为终点，由于中间全部是Base64编码的密文，所以不会有干扰的特殊字符
            if (responseString.charAt(index) == '"' && index != beginPosition) {
                return index + 1;
            }
        }
        //如果没有找到第2个双引号，说明验签内容片段提取失败，直接尝试选取剩余整个响应字符串进行验签
        return responseString.length();
    }

}