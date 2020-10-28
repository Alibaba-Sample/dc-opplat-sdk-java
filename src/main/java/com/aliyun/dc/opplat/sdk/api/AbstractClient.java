/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.SSLException;

import com.aliyun.dc.opplat.sdk.api.common.DcLabHashMap;
import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.common.SignChecker;
import com.aliyun.dc.opplat.sdk.api.common.Signer;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.parser.json.ObjectJsonParser;
import com.aliyun.dc.opplat.sdk.api.internal.parser.xml.ObjectXmlParser;
import com.aliyun.dc.opplat.sdk.api.internal.util.RequestParametersHolder;
import com.aliyun.dc.opplat.sdk.api.internal.util.WebUtils;
import com.aliyun.dc.opplat.sdk.api.internal.util.json.JSONWriter;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;
import com.aliyun.dc.opplat.sdk.api.utils.OpenApiSignature;
import com.aliyun.dc.opplat.sdk.api.utils.StringUtils;

/**
 * SDK端执行抽象类
 *
 * @author changlei.qcl
 * @version $Id: AbstractClient.java, v 0.1 2020年07月28日 2:36 PM changlei.qcl Exp $
 */
public abstract class AbstractClient implements OpplatClient {

    private String serverUrl;

    private String appId;

    private String format = OpplatConstants.FORMAT_JSON;

    private String signType = OpplatConstants.SIGN_TYPE_RSA;

    private String charset;

    private int connectTimeout = 3000;
    private int readTimeout    = 15000;

    public AbstractClient(String serverUrl, String appId, String format,
                          String charset, String signType) {
        this.serverUrl = serverUrl;
        this.appId = appId;
        if (!StringUtils.isEmpty(format)) {
            this.format = format;
        }
        this.charset = charset;
        if (!StringUtils.isEmpty(signType)) {
            this.signType = signType;
        }
    }

    @Override
    public <T extends BaseOpplatResponse> T execute(OpplatRequest<T> request) throws OpplatApiException {
        OpplatParser<T> parser = null;
        if (OpplatConstants.FORMAT_JSON.equals(this.format)) {
            parser = new ObjectJsonParser<T>(request.getResponseClass());
        } else {
            parser = new ObjectXmlParser<T>(request.getResponseClass());
        }
        return actualExecute(request, parser);
    }

    private <T extends BaseOpplatResponse> T actualExecute(OpplatRequest<T> request, OpplatParser<T> parser) throws OpplatApiException {
        long beginTime = System.currentTimeMillis();
        Map<String, Object> rt = doPost(request);
        if (rt == null) {
            return null;
        }
        Map<String, Long> costTimeMap = new HashMap<String, Long>();
        if (rt.containsKey("prepareTime")) {
            costTimeMap.put("prepareCostTime", (Long) (rt.get("prepareTime")) - beginTime);
            if (rt.containsKey("requestTime")) {
                costTimeMap.put("requestCostTime", (Long) (rt.get("requestTime")) - (Long) (rt.get("prepareTime")));
            }
        }
        // 暂无加密的需求，所以返回报文即可见报文
        String responseContent = (String) rt.get("rsp");
        T tRsp = parser.parse(responseContent);

        checkResponseSign(request, parser, responseContent);

        if (costTimeMap.containsKey("requestCostTime")) {
            costTimeMap.put("postCostTime", System.currentTimeMillis() - (Long) (rt.get("requestTime")));
        }

        tRsp.setParams((DcLabHashMap) rt.get("textParams"));

        return tRsp;
    }

    private <T extends BaseOpplatResponse> void checkResponseSign(OpplatRequest<T> request,
                                                                  OpplatParser<T> parser,
                                                                  String responseBody) throws OpplatApiException {
        if (getSignChecker() != null) {
            // 解析出业务返回参数
            SignItem signItem = parser.getSignItem(request, responseBody);
            if (signItem == null) {
                throw new OpplatApiException("sign check fail: Body is Empty!");
            }
            // 仅针对签名不为空
            if (!StringUtils.isEmpty(signItem.getSign())) {
                boolean rsaCheckContent = getSignChecker().check(signItem.getSignSourceDate(),
                        signItem.getSign(), this.signType, this.charset);

                if (!rsaCheckContent) {

                    // 针对JSON \/问题，替换/后再尝试做一次验证
                    if (!StringUtils.isEmpty(signItem.getSignSourceDate())
                            && signItem.getSignSourceDate().contains("\\/")) {

                        String srouceData = signItem.getSignSourceDate().replace("\\/", "/");

                        boolean jsonCheck = getSignChecker().check(srouceData, signItem.getSign(),
                                this.signType, this.charset);

                        if (!jsonCheck) {
                            throw new OpplatApiException(
                                    "sign check fail: check Sign and Data Fail！JSON also！");
                        }
                    } else {

                        throw new OpplatApiException("sign check fail: check Sign and Data Fail!");
                    }
                }
            }

        }
    }

    /**
     * 发送POST请求
     *
     * @param request
     * @param <T>
     * @return
     * @throws OpplatApiException
     */
    private <T extends BaseOpplatResponse> Map<String, Object> doPost(OpplatRequest<T> request) throws OpplatApiException {

        Map<String, Object> result = new HashMap<String, Object>();

        RequestParametersHolder requestHolder = getRequestHolderWithSign(request);
        String url = getRequestUrl(requestHolder);

        result.put("prepareTime", System.currentTimeMillis());

        String rsp = null;
        try {
            rsp = WebUtils.doPost(url, requestHolder.getApplicationParams(), charset,
                    connectTimeout, readTimeout, null, 0);
        } catch (SSLException e) {
            throw new OpplatApiException(e);
        } catch (IOException e) {
            throw new OpplatApiException(e);
        }

        result.put("requestTime", System.currentTimeMillis());
        result.put("rsp", rsp);
        result.put("textParams", requestHolder.getApplicationParams());
        result.put("protocalMustParams", requestHolder.getProtocalMustParams());
        result.put("protocalOptParams", requestHolder.getProtocalOptParams());
        result.put("url", url);
        return result;
    }

    /**
     * 组装接口参数，处理加密、签名逻辑
     *
     * @param request
     * @return
     * @throws OpplatApiException
     */
    private <T extends BaseOpplatResponse> RequestParametersHolder getRequestHolderWithSign(OpplatRequest<?> request)
            throws OpplatApiException {
        RequestParametersHolder requestHolder = new RequestParametersHolder();
        DcLabHashMap appParams = new DcLabHashMap(request.getTextParams());

        // 仅当API包含biz_content参数且值为空时，序列化bizModel填充bizContent
        try {
            if (request.getClass().getMethod("getBizContent") != null
                    && StringUtils.isEmpty(appParams.get(OpplatConstants.BIZ_CONTENT_KEY))
                    && request.getBizModel() != null) {
                appParams.put(OpplatConstants.BIZ_CONTENT_KEY,
                        new JSONWriter().write(request.getBizModel(), true));
            }
        } catch (NoSuchMethodException e) {
            // 找不到getBizContent则什么都不做
        } catch (SecurityException e) {
            //
        }
        requestHolder.setApplicationParams(appParams);

        if (StringUtils.isEmpty(charset)) {
            charset = OpplatConstants.CHARSET_UTF8;
        }

        DateFormat df = new SimpleDateFormat(OpplatConstants.DEFAULT_DATE_FORMAT);
        df.setTimeZone(TimeZone.getTimeZone(OpplatConstants.DATE_TIMEZONE));

        DcLabHashMap protocalMustParams = new DcLabHashMap();
        protocalMustParams.put(OpplatConstants.API_REQ_APP_ID, this.appId);
        protocalMustParams.put(OpplatConstants.API_REQ_CHARSET, this.charset);
        protocalMustParams.put(OpplatConstants.API_REQ_FORMAT, this.format);
        protocalMustParams.put(OpplatConstants.API_REQ_TIME, df.format(new Date(System.currentTimeMillis())));
        protocalMustParams.put(OpplatConstants.API_REQ_SIGN_TYPE, this.signType);
        protocalMustParams.put(OpplatConstants.API_REQ_FUNC, request.getApiMethodName());
        protocalMustParams.put(OpplatConstants.API_REQ_VERSION, request.getApiVersion());
        requestHolder.setProtocalMustParams(protocalMustParams);

        if (null != this.signType) {
            String signContent = OpenApiSignature.getSignatureContent(requestHolder);
            protocalMustParams.put(OpplatConstants.SIGN, getSigner().sign(signContent, signType, charset));
        } else {
            protocalMustParams.put(OpplatConstants.SIGN, "");
        }

        return requestHolder;
    }

    /**
     * 获取POST请求的base url
     *
     * @param requestHolder
     * @return
     * @throws OpplatApiException
     */
    private String getRequestUrl(RequestParametersHolder requestHolder) throws OpplatApiException {
        StringBuilder urlSb = new StringBuilder(serverUrl);
        try {
            String sysMustQuery = WebUtils.buildQuery(requestHolder.getProtocalMustParams(), charset);
            String sysOptQuery = WebUtils.buildQuery(requestHolder.getProtocalOptParams(), charset);

            urlSb.append("?");
            urlSb.append(sysMustQuery);
            if (sysOptQuery != null && sysOptQuery.length() > 0) {
                urlSb.append("&");
                urlSb.append(sysOptQuery);
            }
        } catch (IOException e) {
            throw new OpplatApiException(e);
        }

        return urlSb.toString();
    }

    /**
     * Setter method for property <tt>serverUrl</tt>.
     *
     * @param serverUrl value to be assigned to property serverUrl
     */
    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    /**
     * Setter method for property <tt>appId</tt>.
     *
     * @param appId value to be assigned to property appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * Setter method for property <tt>format</tt>.
     *
     * @param format value to be assigned to property format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Setter method for property <tt>signType</tt>.
     *
     * @param signType value to be assigned to property signType
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * Setter method for property <tt>charset</tt>.
     *
     * @param charset value to be assigned to property charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * Setter method for property <tt>connectTimeout</tt>.
     *
     * @param connectTimeout value to be assigned to property connectTimeout
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Setter method for property <tt>readTimeout</tt>.
     *
     * @param readTimeout value to be assigned to property readTimeout
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public abstract SignChecker getSignChecker();

    public abstract Signer getSigner();
}