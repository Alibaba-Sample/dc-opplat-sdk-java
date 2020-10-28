/**
 * Alipay.com Inc. Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import java.io.Serializable;

/**
 * 响应的解析参数，一般是涉及加密使用，本次暂时涉及加密
 *
 * @author changlei.qcl
 * @version $Id: ResponseParseItem.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public class ResponseParseItem implements Serializable {

    /**  */
    private static final long serialVersionUID = -27707404159419651L;

    /**
     * 加密节点开始
     */
    private int startIndex = -1;

    /**
     * 加密节点结束
     */
    private int endIndex = -1;

    /**
     * 加密内容
     */
    private String encryptContent = null;

    /**
     * @param startIndex
     * @param endIndex
     * @param encryptContent
     */
    public ResponseParseItem(int startIndex, int endIndex, String encryptContent) {
        super();
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.encryptContent = encryptContent;
    }

    /**
     * Getter method for property <tt>startIndex</tt>.
     *
     * @return property value of startIndex
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Getter method for property <tt>endIndex</tt>.
     *
     * @return property value of endIndex
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Getter method for property <tt>encryptContent</tt>.
     *
     * @return property value of encryptContent
     */
    public String getEncryptContent() {
        return encryptContent;
    }

}
