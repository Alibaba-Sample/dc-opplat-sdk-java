package com.aliyun.dc.opplat.sdk.api.enums;

/**
 * @Author: 沈齐
 * @Description:
 * @Date: 2020/10/9
 */
public enum OrderOpEnum {
    ASC("ASC", "升序排列"),

    DESC("DESC", "降序排列"),

    ;

    private String code;

    private String desc;

    OrderOpEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
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
}
