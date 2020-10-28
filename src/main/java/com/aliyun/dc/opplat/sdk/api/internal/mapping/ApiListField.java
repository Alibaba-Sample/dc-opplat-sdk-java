package com.aliyun.dc.opplat.sdk.api.internal.mapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据结构列表属性注解。
 *
 * @author changlei.qcl
 * @version $Id: ApiField.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface ApiListField {

    /**
     * JSON列表属性映射名称
     **/
    String value() default "";

}
