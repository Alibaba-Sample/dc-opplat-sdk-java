/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.alibaba.fastjson.JSON;
import com.aliyun.dc.opplat.sdk.api.DefaultOpplatClient;
import com.aliyun.dc.opplat.sdk.api.OpplatObject;
import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.enums.OrderOpEnum;
import com.aliyun.dc.opplat.sdk.api.enums.QueryOpEnum;
import com.aliyun.dc.opplat.sdk.api.enums.QueryTypeEnum;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.internal.util.WebUtils;
import com.aliyun.dc.opplat.sdk.api.request.DateOpenQueryRequest;
import com.aliyun.dc.opplat.sdk.api.request.dataopen.BizReqModel;
import com.aliyun.dc.opplat.sdk.api.request.dataopen.ConditionModel;
import com.aliyun.dc.opplat.sdk.api.request.dataopen.OrderParamModel;
import com.aliyun.dc.opplat.sdk.api.request.dataopen.QueryConditionModel;
import com.aliyun.dc.opplat.sdk.api.response.DateOpenQueryResponse;

/**
 * @author changlei.qcl
 * @version $Id: DefaultOpplatClientTest.java, v 0.1 2020年07月29日 10:46 AM changlei.qcl Exp $
 */
public class ExecuteTest {

    private DefaultOpplatClient opplatClient;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        opplatClient = new DefaultOpplatClient(TestAccount.LOCAL.GATEWAYT_URL, TestAccount.LOCAL.APP_ID, TestAccount.LOCAL.APP_PRIVATE_KEY,
                OpplatConstants.FORMAT_JSON, OpplatConstants.CHARSET_UTF8, TestAccount.LOCAL.PLAT_PUBLICKEY, OpplatConstants.SIGN_TYPE_RSA2);
    }

    @After
    public void tearDown() {
        WebUtils.setNeedCheckServerTrusted(true);
        WebUtils.setKeepAliveTimeout(0);
    }

    /**
     * 字符串格式报文
     * 查询DETAIL
     */
    @Test
    public void should_be_able_to_send_token() throws OpplatApiException {
        DateOpenQueryResponse response = opplatClient.execute(createQueryDetailRequest2());
        System.out.println(response);
    }

    /**
     * 字符串格式报文
     * 分页查询COUNT_AND_DETAIL
     */
    @Test
    public void pageQuery() throws OpplatApiException {
        DateOpenQueryResponse response = opplatClient.execute(createPageQueryDetailRequest());
        System.out.println(response);
    }

    /**
     * 字符串格式报文
     * 查询COUNT
     */
    @Test
    public void countQuery() throws OpplatApiException {
        DateOpenQueryResponse response = opplatClient.execute(createQueryCountRequest());
        System.out.println(response);
    }

    /**
     * 使用model拼接请求报文
     * 查询DETAIL
     */
    @Test
    public void testSetModelRequest() throws OpplatApiException {
        DateOpenQueryResponse response = opplatClient.execute(createBizModelRequest(createDateOpenModel()));
        System.out.println("返回结果:" + response.getCode());
    }

    /**
     * 使用model拼接请求报文
     * 查询字段为空
     */
    @Test
    public void testNoParamRequest() throws OpplatApiException {
        DateOpenQueryResponse response = opplatClient.execute(createBizModelRequest(createNullParamModel()));
        System.out.println("返回结果:"  + response.getCode());
    }


    private DateOpenQueryRequest createBizModelRequest(OpplatObject bizModel) {
        DateOpenQueryRequest request = new DateOpenQueryRequest(TestAccount.LOCAL.API_CODE,TestAccount.LOCAL.API_VERSION);
        request.setBizModel(bizModel);
        return request;
    }


    private OpplatObject createNullParamModel() {
        BizReqModel bizReqModel = new BizReqModel();
        bizReqModel.setQueryType(QueryTypeEnum.DETAIL.getCode());
        bizReqModel.setLimit(100L);
        bizReqModel.addOrderParam(new OrderParamModel("name1", OrderOpEnum.ASC.getCode(), 1));
        List<QueryConditionModel> queryConditions = new ArrayList<>();
        QueryConditionModel name1 = new QueryConditionModel("name1");
        name1.addConditionModel(new ConditionModel(QueryOpEnum.LE.getCode(), "20191201" ));
        name1.addConditionModel(new ConditionModel(QueryOpEnum.GE.getCode(), "20181201" ));

        QueryConditionModel name2 = new QueryConditionModel("name2");
        name2.addConditionModel(new ConditionModel(QueryOpEnum.IN.getCode(), JSON.toJSONString(Arrays.asList("330521001","330521002"))));

        queryConditions.add(name1);
        queryConditions.add(name2);
        bizReqModel.setQueryConditionInfos(queryConditions);
        return bizReqModel;
    }

    private OpplatObject createDateOpenModel() {
        BizReqModel bizReqModel = new BizReqModel();
        bizReqModel.setQueryType(QueryTypeEnum.COUNT.getCode());
        bizReqModel.setLimit(100L);
        List<String> returnParms = new ArrayList<>();
        returnParms.add("name1");
        returnParms.add("name2");
        returnParms.add("name3");
        bizReqModel.setReturnParams(JSON.toJSONString(returnParms));
        bizReqModel.addOrderParam(new OrderParamModel("name1", OrderOpEnum.ASC.getCode(), 1));
        List<QueryConditionModel> queryConditions = new ArrayList<>();
        QueryConditionModel name1 = new QueryConditionModel("name1");
        name1.addConditionModel(new ConditionModel(QueryOpEnum.LE.getCode(),"20191201" ));
        name1.addConditionModel(new ConditionModel(QueryOpEnum.GE.getCode(),"20181201" ));


        QueryConditionModel name2 = new QueryConditionModel("name2");
        name2.addConditionModel(new ConditionModel(QueryOpEnum.EQ.getCode(), "330521001"));

        queryConditions.add(name1);
        queryConditions.add(name2);
        bizReqModel.setQueryConditionInfos(queryConditions);
        return bizReqModel;
    }


    private DateOpenQueryRequest createPageQueryDetailRequest() {
        DateOpenQueryRequest request = new DateOpenQueryRequest(TestAccount.LOCAL.API_CODE,TestAccount.LOCAL.API_VERSION);
        String bizContent =
                "{\"queryType\":\"COUNT_AND_DETAIL\",\"query_condition_infos\":[{\"condition_infos\":[{\"op\":\"le\",\"value\":\"20191201\"},{\"op\":\"ge\",\"value\":\"20181201\"}],\"name\":\"name1\"},{\"condition_infos\":[{\"op\":\"in\",\"value\":\"[\\\"330521001\\\",\\\"330521002\\\",\\\"330521003\\\"]\"}],\"name\":\"name2\"}],\"order_parm_infos\":[{\"op\":\"asc\",\"name\":\"name1\",\"priority\":\"1\"},{\"op\":\"desc\",\"name\":\"name2\",\"priority\":\"2\"}],\"return_parms\":\"[\\\"name1\\\",\\\"name2\\\",\\\"name3\\\",\\\"name4\\\",\\\"name5\\\"]\",\"pageNum\":1,\"pageSize\":20}";
        request.setBizContent(bizContent);
        return request;
    }

    private DateOpenQueryRequest createQueryCountRequest() {
        DateOpenQueryRequest request = new DateOpenQueryRequest(TestAccount.LOCAL.API_CODE,TestAccount.LOCAL.API_VERSION);
        String bizContent = "{\"queryType\":\"COUNT\",\"query_condition_infos\":[{\"condition_infos\":[{\"op\":\"le\",\"value\":\"20191201\"},{\"op\":\"ge\",\"value\":\"20181201\"}],\"name\":\"name1\"},{\"condition_infos\":[{\"op\":\"in\",\"value\":\"[\\\"330521001\\\",\\\"330521002\\\",\\\"330521003\\\"]\"}],\"name\":\"name2\"}],\"order_parm_infos\":[{\"op\":\"asc\",\"name\":\"name1\",\"priority\":\"1\"},{\"op\":\"desc\",\"name\":\"name2\",\"priority\":\"2\"}],\"return_parms\":\"[\\\"name1\\\",\\\"name2\\\",\\\"name3\\\",\\\"name4\\\",\\\"name5\\\"]\",\"limit\":100}";
        request.setBizContent(bizContent);
        return request;
    }

    private DateOpenQueryRequest createQueryDetailRequest() {
        DateOpenQueryRequest request = new DateOpenQueryRequest(TestAccount.LOCAL.API_CODE,TestAccount.LOCAL.API_VERSION);
        String bizContent = "{\n" +
                "    \"queryType\":\"DETAIL\",\n" +
                "    \"query_condition_infos\":[\n" +
                "        {\n" +
                "            \"condition_infos\":[\n" +
                "                {\n" +
                "                    \"op\":\"eq\",\n" +
                "                    \"value\":\"100\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"name\":\"cardId\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"condition_infos\":[\n" +
                "                {\n" +
                "                    \"op\":\"eq\",\n" +
                "                    \"value\":\"1\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"name\":\"type\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"return_parms\":\"[\"value\",\"cardId\",\"type\"]\",\n" +
                "    \"limit\":100\n" +
                "}";
        request.setBizContent(bizContent);
        return request;
    }

    private DateOpenQueryRequest createQueryDetailRequest2() {
        DateOpenQueryRequest request = new DateOpenQueryRequest(TestAccount.LOCAL.API_CODE,TestAccount.LOCAL.API_VERSION);
        BizReqModel bizReqModel = new BizReqModel();
        bizReqModel.setQueryType(QueryTypeEnum.COUNT_AND_DETAIL.getCode());
        bizReqModel.setLimit(100L);
        bizReqModel.setReturnParams(JSON.toJSONString(new String[]{"cardId", "type", "value"}));
        bizReqModel.addOrderParam(new OrderParamModel("cardId", OrderOpEnum.ASC.getCode(), 1));
        bizReqModel.addOrderParam(new OrderParamModel("type", OrderOpEnum.ASC.getCode(), 2));
        List<QueryConditionModel> queryConditionModels = new ArrayList<>();
        bizReqModel.setQueryConditionInfos(queryConditionModels);

        QueryConditionModel cardId = new QueryConditionModel("cardId");
        cardId.addConditionModel(new ConditionModel(QueryOpEnum.EQ.getCode(), "110"));
        queryConditionModels.add(cardId);

        QueryConditionModel type = new QueryConditionModel("type");
        type.addConditionModel(new ConditionModel(QueryOpEnum.EQ.getCode(), "1"));
//        queryConditionModels.add(type);
        request.setBizModel(bizReqModel);
        return request;
    }

}