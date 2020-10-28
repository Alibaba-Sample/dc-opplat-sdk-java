package com.aliyun.dc.opplat.sdk.api.internal.parser.xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import com.aliyun.dc.opplat.sdk.api.SignItem;
import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converter;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Converters;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.Reader;
import com.aliyun.dc.opplat.sdk.api.internal.util.XmlUtils;
import com.aliyun.dc.opplat.sdk.api.request.OpplatRequest;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;
import com.aliyun.dc.opplat.sdk.api.utils.StringUtils;

/**
 * XML 格式转换器。【暂不支持XML】
 */
public class XmlConverter implements Converter {

    @Override
    public <T extends BaseOpplatResponse> T toResponse(String rsp, Class<T> clazz) throws OpplatApiException {
        Element root = XmlUtils.getRootElementFromString(rsp);
        return getModelFromXML(root, clazz);
    }

    private <T> T getModelFromXML(final Element element, Class<T> clazz) throws OpplatApiException {
        if (element == null) { return null; }

        return Converters.convert(clazz, new Reader() {

            @Override
            public boolean hasReturnField(Object name) {
                Element childE = XmlUtils.getChildElement(element, (String) name);
                return childE != null;
            }

            @Override
            public Object getPrimitiveObject(Object name) {
                return XmlUtils.getElementValue(element, (String) name);
            }

            @Override
            public Object getObject(Object name, Class<?> type) throws OpplatApiException {
                Element childE = XmlUtils.getChildElement(element, (String) name);
                if (childE != null) {
                    return getModelFromXML(childE, type);
                } else {
                    return null;
                }
            }

            @Override
            public List<?> getListObjects(Object listName, Object itemName) {
                return null;
            }

            @Override
            public List<?> getListObjects(Object listName, Object itemName, Class<?> subType)
                    throws OpplatApiException {
                List<Object> list = null;
                Element listE = XmlUtils.getChildElement(element, (String) listName);

                if (listE != null) {
                    list = new ArrayList<Object>();
                    List<Element> itemEs = XmlUtils.getChildElements(listE, (String) itemName);
                    for (Element itemE : itemEs) {
                        Object obj = null;
                        String value = XmlUtils.getElementValue(itemE);

                        if (String.class.isAssignableFrom(subType)) {
                            obj = value;
                        } else if (Long.class.isAssignableFrom(subType)) {
                            obj = Long.valueOf(value);
                        } else if (Integer.class.isAssignableFrom(subType)) {
                            obj = Integer.valueOf(value);
                        } else if (Boolean.class.isAssignableFrom(subType)) {
                            obj = Boolean.valueOf(value);
                        } else if (Date.class.isAssignableFrom(subType)) {
                            DateFormat format = new SimpleDateFormat(OpplatConstants.DEFAULT_DATE_FORMAT);
                            try {
                                obj = format.parse(value);
                            } catch (ParseException e) {
                                throw new OpplatApiException(e);
                            }
                        } else {
                            obj = getModelFromXML(itemE, subType);
                        }
                        if (obj != null) { list.add(obj); }
                    }
                }
                return list;
            }
        });
    }

    @Override
    public SignItem getSignItem(OpplatRequest<?> request, String responseBody)
            throws OpplatApiException {

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
    private String getSignSourceData(OpplatRequest<?> request, String body) {

        // XML不同的节点
        String rootNode = request.getApiMethodName().replace('.', '_') + OpplatConstants.RESPONSE_SUFFIX;

        int indexOfRootNode = body.indexOf(rootNode);

        // 成功或者新版接口
        if (indexOfRootNode > 0) {

            return parseSignSourceData(body, rootNode, indexOfRootNode);
        }
        return null;
    }

    /**
     * 获取签名
     *
     * @param body
     * @return
     */
    private String getSign(String body) {

        String signNodeName = "<" + OpplatConstants.SIGN + ">";
        String signEndNodeName = "</" + OpplatConstants.SIGN + ">";

        int indexOfSignNode = body.indexOf(signNodeName);
        int indexOfSignEndNode = body.indexOf(signEndNodeName);

        if (indexOfSignNode < 0 || indexOfSignEndNode < 0) {
            return null;
        }

        //  签名
        return body.substring(indexOfSignNode + signNodeName.length(), indexOfSignEndNode);
    }

    /**
     * 签名源串
     *
     * @param body
     * @param rootNode
     * @param indexOfRootNode
     * @return
     */
    private String parseSignSourceData(String body, String rootNode, int indexOfRootNode) {

        //  第一个字母+长度+>
        int signDataStartIndex = indexOfRootNode + rootNode.length() + 1;
        int indexOfSign = body.indexOf("<" + OpplatConstants.SIGN);

        if (indexOfSign < 0) {
            return null;
        }

        int signDataEndIndex = indexOfSign;

        return body.substring(signDataStartIndex, signDataEndIndex);
    }

}
