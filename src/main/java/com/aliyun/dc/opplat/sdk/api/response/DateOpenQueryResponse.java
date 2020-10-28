/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.response;

import java.util.List;
import java.util.Map;

import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiListField;

/**
 * 数据开放的平台查询响应
 *
 * @author changlei.qcl
 * @version $Id: DateOpenQueryResponse.java, v 0.1 2020年07月29日 11:05 AM changlei.qcl Exp $
 */
public class DateOpenQueryResponse extends BaseOpplatResponse {
    private static final long serialVersionUID = -2854938037188891330L;

    @ApiListField("detail_infos")
    private List<Map<String, Object>> detailInfos;

    @ApiField("total_count")
    private Long count;

    /**
     * Getter method for property <tt>detailInfos</tt>.
     *
     * @return property value of detailInfos
     */
    public List getDetailInfos() {
        return detailInfos;
    }

    /**
     * Setter method for property <tt>detailInfos</tt>.
     *
     * @param detailInfos value to be assigned to property detailInfos
     */
    public void setDetailInfos(List detailInfos) {
        this.detailInfos = detailInfos;
    }

    /**
     * Getter method for property <tt>count</tt>.
     *
     * @return property value of count
     */
    public Long getCount() {
        return count;
    }

    /**
     * Setter method for property <tt>count</tt>.
     *
     * @param count value to be assigned to property count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DateOpenQueryResponse(super=" +
                super.toString() + "," +
                "detailInfos=" + detailInfos +
                ", count=" + count +
                ")";
    }

}