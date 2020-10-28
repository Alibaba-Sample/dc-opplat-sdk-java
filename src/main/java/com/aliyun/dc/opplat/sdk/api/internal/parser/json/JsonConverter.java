/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.parser.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.aliyun.dc.opplat.sdk.api.SignItem;
import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converter;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converters;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Reader;
import com.aliyun.dc.opplat.sdk.api.internal.util.SignSourceData;
import com.aliyun.dc.opplat.sdk.api.internal.util.json.BaseJSONReader;
import com.aliyun.dc.opplat.sdk.api.internal.util.json.ExceptionErrorListener;
import com.aliyun.dc.opplat.sdk.api.internal.util.json.JSONValidatingReader;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;
import com.aliyun.dc.opplat.sdk.api.utils.OpenApiSignature;
import com.aliyun.dc.opplat.sdk.api.utils.StringUtils;

/**
 * JSON格式转换器。
 *
 * @author changlei.qcl
 * @version $Id: JsonConverter.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public class JsonConverter implements Converter {

    @Override
    public <T extends BaseOpplatResponse> T toResponse(String rsp, Class<T> clazz) throws OpplatApiException {
        BaseJSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
        Object rootObj = reader.read(rsp);
        if (rootObj instanceof Map<?, ?>) {
            Map<?, ?> rootJson = (Map<?, ?>) rootObj;
            Collection<?> values = rootJson.values();
            for (Object rspObj : values) {
                if (rspObj instanceof Map<?, ?>) {
                    Map<?, ?> rspJson = (Map<?, ?>) rspObj;
                    return fromJson(rspJson, clazz);
                }
            }
        }
        return null;
    }

    @Override
    public SignItem getSignItem(OpplatRequest<?> request, String responseBody) throws OpplatApiException {
        // 响应为空则直接返回
        if (StringUtils.isEmpty(responseBody)) {

            return null;
        }

        SignItem signItem = new SignItem();

        // 获取签名
        String sign = getSign(responseBody);
        signItem.setSign(sign);

        // 签名源串
        String signSourceData = getSignSourceData(request, responseBody);
        signItem.setSignSourceDate(signSourceData);

        return signItem;
    }

    /**
     * @param request
     * @param body
     * @return
     */
    private String getSignSourceData(OpplatRequest<?> request, String body) throws OpplatApiException {

        // 加签源串起点
        String rootNode = request.getApiMethodName().replace('.', '_')
                + OpplatConstants.RESPONSE_SUFFIX;

        int indexOfRootNode = body.indexOf(rootNode);

        // 成功或者新版接口
        if (indexOfRootNode > 0) {

            return parseSignSourceData(body, rootNode, indexOfRootNode);

        } else {
            return null;
        }
    }

    /**
     * 获取签名源串内容
     *
     * @param body
     * @param rootNode
     * @param indexOfRootNode
     * @return
     */
    private String parseSignSourceData(String body, String rootNode, int indexOfRootNode) throws OpplatApiException {

        //第一个字母+长度+冒号+引号
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 2;

        int indexOfSign = body.indexOf("\"" + OpplatConstants.SIGN + "\"");
        if (indexOfSign < 0) {
            return null;
        }

        SignSourceData signSourceData = OpenApiSignature.extractSignContent(body, signDataStartIndex);

        //如果提取的待验签原始内容后还有root
        if (body.lastIndexOf(rootNode) > signSourceData.getEndIndex()) {
            throw new OpplatApiException("检测到响应报文中有重复的" + rootNode + "，验签失败。");
        }

        return signSourceData.getSourceData();
    }

    /**
     * 获取签名
     *
     * @param body
     * @return
     */
    private String getSign(String body) {

        BaseJSONReader reader = new JSONValidatingReader(new ExceptionErrorListener());
        Object rootObj = reader.read(body);
        Map<?, ?> rootJson = (Map<?, ?>) rootObj;

        return (String) rootJson.get(OpplatConstants.SIGN);
    }

    /**
     * 把JSON格式的数据转换为对象。
     *
     * @param <T>   泛型领域对象
     * @param json  JSON格式的数据
     * @param clazz 泛型领域类型
     * @return 领域对象
     * @throws OpplatApiException
     */
    public <T> T fromJson(final Map<?, ?> json, Class<T> clazz) throws OpplatApiException {
        return Converters.convert(clazz, new Reader() {
            @Override
            public boolean hasReturnField(Object name) {
                return json.containsKey(name);
            }

            @Override
            public Object getPrimitiveObject(Object name) {
                return json.get(name);
            }

            @Override
            public Object getObject(Object name, Class<?> type) throws OpplatApiException {
                Object tmp = json.get(name);
                if (tmp instanceof Map<?, ?>) {
                    Map<?, ?> map = (Map<?, ?>) tmp;
                    return fromJson(map, type);
                } else {
                    return null;
                }
            }

            @Override
            public List<?> getListObjects(Object listName, Object itemName) {
                List<Object> listObjs = null;
                Object listTmp = json.get(listName);
                if(listTmp == null){
                    listTmp = json.get(itemName);
                }
                if(listTmp != null && listTmp instanceof List){
                    listObjs = (List<Object>) listTmp;
                }
                return listObjs;
            }

            @Override
            public List<?> getListObjects(Object listName, Object itemName, Class<?> subType) throws OpplatApiException {
                List<Object> listObjs = null;

                Object listTmp = json.get(listName);
                if (listTmp instanceof Map<?, ?>) {
                    Map<?, ?> jsonMap = (Map<?, ?>) listTmp;
                    Object itemTmp = jsonMap.get(itemName);
                    if (itemTmp == null && listName != null) {
                        String listNameStr = listName.toString();
                        itemTmp = jsonMap.get(listNameStr.substring(0, listNameStr.length() - 1));
                    }
                    if (itemTmp instanceof List<?>) {
                        listObjs = getListObjectsInner(subType, itemTmp);
                    }
                } else if (listTmp instanceof List<?>) {
                    listObjs = getListObjectsInner(subType, listTmp);
                }

                return listObjs;
            }

            private List<Object> getListObjectsInner(Class<?> subType, Object itemTmp)
                    throws OpplatApiException {
                List<Object> listObjs;
                listObjs = new ArrayList<Object>();
                List<?> tmpList = (List<?>) itemTmp;
                for (Object subTmp : tmpList) {
                    Object obj = null;
                    if (String.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if (Long.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if (Integer.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if (Boolean.class.isAssignableFrom(subType)) {
                        obj = subTmp;
                    } else if (Date.class.isAssignableFrom(subType)) {
                        DateFormat format = new SimpleDateFormat(OpplatConstants.DEFAULT_DATE_FORMAT);
                        try {
                            obj = format.parse(String.valueOf(subTmp));
                        } catch (ParseException e) {
                            throw new OpplatApiException(e);
                        }
                    } else if (subTmp instanceof Map<?, ?>) {// object
                        Map<?, ?> subMap = (Map<?, ?>) subTmp;
                        obj = fromJson(subMap, subType);
                    }

                    if (obj != null) {
                        listObjs.add(obj);
                    }
                }
                return listObjs;
            }

        });
    }
}