/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.request.dataopen;

import java.util.ArrayList;
import java.util.List;

import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiField;
import com.aliyun.dc.opplat.sdk.api.internal.mapping.ApiListField;

/**
 * 业务请求体Model
 *
 * @author changlei.qcl
 * @version $Id: BizReqModel.java, v 0.1 2020年08月05日 11:38 AM changlei.qcl Exp $
 */
public class BizReqModel extends OpplatObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 查询类型：DETAIL-查询明细； COUNT-查询数量
     */
    @ApiField("query_type")
    private String queryType;

    /**
     * 查询参数
     */
    @ApiListField("query_condition_infos")
    private List<QueryConditionModel> queryConditionInfos;

    /**
     * 排序参数
     */
    @ApiListField("order_parm_infos")
    private List<OrderParamModel> orderParamInfos;

    /**
     * 返回参数，必须是JSON的集合字符串形式
     */
    @ApiField("return_parms")
    private String returnParams;

    @ApiField("limit")
    private Long limit;

    @ApiField("pageNum")
    private Long pageNum;

    @ApiField("pageSize")
    private Long pageSize;

    /**
     * 添加排序参数
     * @param orderParamModel
     */
    public void addOrderParam(OrderParamModel orderParamModel) {
        if (null == this.orderParamInfos) {
            this.orderParamInfos = new ArrayList<>();
        }
        this.orderParamInfos.add(orderParamModel);
    }

    /**
     * Getter method for property <tt>queryType</tt>.
     *
     * @return property value of queryType
     */
    public String getQueryType() {
        return queryType;
    }

    /**
     * Setter method for property <tt>queryType</tt>.
     *
     * @param queryType value to be assigned to property queryType
     */
    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    /**
     * Getter method for property <tt>queryConditionInfos</tt>.
     *
     * @return property value of queryConditionInfos
     */
    public List<QueryConditionModel> getQueryConditionInfos() {
        return queryConditionInfos;
    }

    /**
     * Setter method for property <tt>queryConditionInfos</tt>.
     *
     * @param queryConditionInfos value to be assigned to property queryConditionInfos
     */
    public void setQueryConditionInfos(List<QueryConditionModel> queryConditionInfos) {
        this.queryConditionInfos = queryConditionInfos;
    }

    /**
     * Getter method for property <tt>orderParamInfos</tt>.
     *
     * @return property value of orderParamInfos
     */
    public List<OrderParamModel> getOrderParamInfos() {
        return orderParamInfos;
    }

    /**
     * Setter method for property <tt>orderParamInfos</tt>.
     *
     * @param orderParamInfos value to be assigned to property orderParamInfos
     */
    public void setOrderParamInfos(List<OrderParamModel> orderParamInfos) {
        this.orderParamInfos = orderParamInfos;
    }

    /**
     * Getter method for property <tt>returnParams</tt>.
     *
     * @return property value of returnParams
     */
    public String getReturnParams() {
        return returnParams;
    }

    /**
     * Setter method for property <tt>returnParams</tt>.
     *
     * @param returnParams value to be assigned to property returnParams
     */
    public void setReturnParams(String returnParams) {
        this.returnParams = returnParams;
    }

    /**
     * Getter method for property <tt>limit</tt>.
     *
     * @return property value of limit
     */
    public Long getLimit() {
        return limit;
    }

    /**
     * Setter method for property <tt>limit</tt>.
     *
     * @param limit value to be assigned to property limit
     */
    public void setLimit(Long limit) {
        this.limit = limit;
    }

    /**
     * Getter method for property <tt>pageNum</tt>.
     *
     * @return property value of pageNum
     */
    public Long getPageNum() {
        return pageNum;
    }

    /**
     * Setter method for property <tt>pageNum</tt>.
     *
     * @param pageNum value to be assigned to property pageNum
     */
    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     *
     * @return property value of pageSize
     */
    public Long getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     *
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}