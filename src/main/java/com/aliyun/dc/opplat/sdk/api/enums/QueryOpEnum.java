/**
 * Alipay.com Inc. Copyright (c) 2004-2020 All Rights Reserved.
 */
package com.aliyun.dc.opplat.sdk.api.enums;


import java.util.List;

/**
 * 查询条件的枚举
 */
public enum QueryOpEnum {

    LT("lt", "<", "小于 <", String.class),
    LE("le", "<=", "小于等于 <=", String.class),
    GT("gt", ">", "大于 >", String.class),
    GE("ge", ">=", "大于等于 >=", String.class),
    EQ("eq", "=", "等于", String.class),
    NEQ("neq", "<>", "不等于", String.class),
    IN("in", "FIND_IN_SET", "包含", List.class),
    NOT_IN("not_in", "NOT FIND_IN_SET", "不包含", List.class),
    IS_NULL("is_null", "IS NULL", "为空", null),
    NOT_NULL("not_null", "IS NOT NULL", "不为空", null),
    ;

    private String code;

    private String exp;

    private String desc;

    private Class clazz;

    QueryOpEnum(String code, String exp, String desc, Class clazz) {
        this.code = code;
        this.exp = exp;
        this.desc = desc;
        this.clazz = clazz;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>exp</tt>.
     *
     * @return property value of exp
     */
    public String getExp() {
        return exp;
    }

    /**
     * Setter method for property <tt>exp</tt>.
     *
     * @param exp value to be assigned to property exp
     */
    public void setExp(String exp) {
        this.exp = exp;
    }

    /**
     * Getter method for property <tt>desc</tt>.
     *
     * @return property value of desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Setter method for property <tt>desc</tt>.
     *
     * @param desc value to be assigned to property desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Getter method for property <tt>clazz</tt>.
     *
     * @return property value of clazz
     */
    public Class getClazz() {
        return clazz;
    }

    /**
     * Setter method for property <tt>clazz</tt>.
     *
     * @param clazz value to be assigned to property clazz
     */
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}