/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.internal.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author changlei.qcl
 * @version $Id: RequestProcess.java, v 0.1 2020年07月29日 1:21 PM changlei.qcl Exp $
 */
public class RequestProcess {

    public static Map<String, String> headersFromRequest(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            headers.put(key, value);
        }
        return headers;
    }

    /**
     * 把request中的参数转换为Map格式
     * @param request
     * @return
     */
    public static Map<String, String> getParameterStringMap(HttpServletRequest request) {
        //把请求参数封装到Map<String, String[]>中
        Map<String, String[]> properties = request.getParameterMap();
        Map<String, String> returnMap = new HashMap<>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length > 1) {
                //用于请求参数中有多个相同名称
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                //用于请求参数中请求参数名唯一
                value = values[0];
            }
            returnMap.put(name, value);

        }
        return returnMap;
    }

}