package com.aliyun.dc.opplat.sdk.api.internal.mapping;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.aliyun.dc.opplat.sdk.api.common.OpplatConstants;
import com.aliyun.dc.opplat.sdk.api.exception.OpplatApiException;
import com.aliyun.dc.opplat.sdk.api.response.BaseOpplatResponse;
import com.aliyun.dc.opplat.sdk.api.utils.StringUtils;

/**
 * 转换工具类。
 *
 * @author changlei.qcl
 * @version $Id: ApiField.java, v 0.1 2020年07月28日 9:12 PM changlei.qcl Exp $
 */
public class Converters {
    // 是否对JSON返回的数据类型进行校验，默认不校验。给内部测试JSON返回时用的开关。
    //规则：返回的"基本"类型只有String,Long,Boolean,Date,采取严格校验方式，如果类型不匹配，报错
    public static boolean isCheckJsonType = false;

    private static final Set<String> baseFields = new HashSet<String>();

    private static final Set<String> excludeFields = new HashSet<String>();

    /**
     * 被子类覆盖的属性
     */
    private static final Set<String> overideFields = new HashSet<String>();

    static {
        baseFields.add("code");
        baseFields.add("msg");
        baseFields.add("subCode");
        baseFields.add("subMsg");
        baseFields.add("status");
        baseFields.add("params");
        baseFields.add("success");
    }

    static {
        excludeFields.add("errorCode");
    }

    static {

        overideFields.add("code");
        overideFields.add("msg");
    }

    private Converters() {
    }

    /**
     * 使用指定 的读取器去转换字符串为对象。
     *
     * @param <T>    领域泛型
     * @param clazz  领域类型
     * @param reader 读取器
     * @return 领域对象
     * @throws OpplatApiException
     */
    public static <T> T convert(Class<T> clazz, Reader reader) throws OpplatApiException {
        T rsp = null;

        try {
            rsp = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

            boolean isResponseClazz = BaseOpplatResponse.class.isAssignableFrom(clazz);

            for (PropertyDescriptor pd : pds) {
                Method writeMethod = pd.getWriteMethod();
                if (writeMethod == null) {
                    // ignore read-only fields
                    continue;
                }

                String itemName = pd.getName();
                String listName = null;

                // 之前errorCode的属性要剔除掉
                if (isResponseClazz && excludeFields.contains(itemName)) {
                    continue;
                }

                List<OpplatFieldMethod> opplatFieldMethods = new ArrayList<>();

                if (baseFields.contains(itemName) && isResponseClazz) {
                    Field field = BaseOpplatResponse.class.getDeclaredField(itemName);
                    OpplatFieldMethod opplatFieldMethod = new OpplatFieldMethod();
                    opplatFieldMethod.setField(field);

                    // writeMethod属于父类，则直接使用
                    if (writeMethod.getDeclaringClass().getName().contains("AlipayResponse")) {
                        opplatFieldMethod.setMethod(writeMethod);
                    } else {
                        // 否则从父类再取一次
                        writeMethod = tryGetSetMethod(BaseOpplatResponse.class, field, writeMethod.getName());
                        if (writeMethod == null) {
                            continue;
                        }
                        opplatFieldMethod.setMethod(writeMethod);
                    }
                    opplatFieldMethods.add(opplatFieldMethod);

                    // 如果被子类覆盖的，则从尝试从子类中获取
                    if (overideFields.contains(itemName)) {
                        field = tryGetFieldWithoutExp(clazz, itemName);
                        // 属性存在则需要重新从子类获取访问方法
                        if (field != null) {
                            writeMethod = tryGetSetMethod(clazz, field, writeMethod.getName());
                            if (writeMethod == null) {
                                continue;
                            }
                            opplatFieldMethod = new OpplatFieldMethod();
                            opplatFieldMethod.setField(field);
                            opplatFieldMethod.setMethod(writeMethod);
                            opplatFieldMethods.add(opplatFieldMethod);
                        }
                    }
                } else {
                    Field field = clazz.getDeclaredField(itemName);
                    OpplatFieldMethod opplatFieldMethod = new OpplatFieldMethod();
                    opplatFieldMethod.setField(field);
                    opplatFieldMethod.setMethod(writeMethod);
                    opplatFieldMethods.add(opplatFieldMethod);
                }

                // 迭代设置属性
                for (OpplatFieldMethod opplatFieldMethod : opplatFieldMethods) {
                    Field field = opplatFieldMethod.getField();
                    Method method = opplatFieldMethod.getMethod();

                    ApiField jsonField = field.getAnnotation(ApiField.class);
                    if (jsonField != null) {
                        itemName = jsonField.value();
                    }
                    ApiListField jsonListField = field.getAnnotation(ApiListField.class);
                    if (jsonListField != null) {
                        listName = jsonListField.value();
                    }

                    if (!reader.hasReturnField(itemName)) {
                        if (listName == null || !reader.hasReturnField(listName)) {
                            // ignore non-return field
                            continue;
                        }
                    }

                    Class<?> typeClass = field.getType();
                    // 目前
                    if (String.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof String) {
                            method.invoke(rsp, value.toString());
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a String");
                            }
                            if (value != null) {
                                method.invoke(rsp, value.toString());
                            } else {
                                method.invoke(rsp, "");
                            }
                        }
                    } else if (Long.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof Long) {
                            method.invoke(rsp, (Long) value);
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a Number(Long)");
                            }
                            if (StringUtils.isNumeric(value)) {
                                method.invoke(rsp, Long.valueOf(value.toString()));
                            }
                        }
                    } else if (Integer.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof Integer) {
                            method.invoke(rsp, (Integer) value);
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a Number(Integer)");
                            }
                            if (StringUtils.isNumeric(value)) {
                                method.invoke(rsp, Integer.valueOf(value.toString()));
                            }
                        }
                    } else if (Boolean.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof Boolean) {
                            method.invoke(rsp, (Boolean) value);
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a Boolean");
                            }
                            if (value != null) {
                                method.invoke(rsp, Boolean.valueOf(value.toString()));
                            }
                        }
                    } else if (Double.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof Double) {
                            method.invoke(rsp, (Double) value);
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a Double");
                            }
                        }
                    } else if (Number.class.isAssignableFrom(typeClass)) {
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof Number) {
                            method.invoke(rsp, (Number) value);
                        } else {
                            if (isCheckJsonType && value != null) {
                                throw new OpplatApiException(itemName + " is not a Number");
                            }
                        }
                    } else if (Date.class.isAssignableFrom(typeClass)) {
                        DateFormat format = new SimpleDateFormat(OpplatConstants.DEFAULT_DATE_FORMAT);
                        format.setTimeZone(TimeZone.getTimeZone(OpplatConstants.DATE_TIMEZONE));
                        Object value = reader.getPrimitiveObject(itemName);
                        if (value instanceof String) {
                            method.invoke(rsp, format.parse(value.toString()));
                        }
                    } else if (List.class.isAssignableFrom(typeClass)) {
                        Type fieldType = field.getGenericType();
                        if (fieldType instanceof ParameterizedType) {
                            ParameterizedType paramType = (ParameterizedType) fieldType;
                            Type[] genericTypes = paramType.getActualTypeArguments();
                            if (genericTypes != null && genericTypes.length > 0 && genericTypes[0] instanceof ParameterizedType) {
                                ParameterizedType paramType2 = (ParameterizedType) genericTypes[0];
                                Type[] genericTypes2 = paramType2.getActualTypeArguments();
                                if (genericTypes2 != null && genericTypes2.length > 0){
                                    List<?> listObjs = reader.getListObjects(listName, itemName);
                                    if (listObjs != null) {
                                        method.invoke(rsp, listObjs);
                                    }
                                }
//                                if (genericTypes[0] instanceof Map<?, ?>) {
//                                    Class<?> subType = (Class<?>) genericTypes[0];
//                                    List<?> listObjs = reader.getListObjects(listName, itemName, subType);
//                                    if (listObjs != null) {
//                                        method.invoke(rsp, listObjs);
//                                    }
//                                }
                            }
                        }
                    } else {
                        Object obj = reader.getObject(itemName, typeClass);
                        if (obj != null) {
                            method.invoke(rsp, obj);
                        }
                    }
                }

            }
        } catch (Exception e) {
            throw new OpplatApiException(e);
        }

        return rsp;
    }

    /**
     * 尝试获取属性
     * <p>
     * 不会抛出异常，不存在则返回null
     *
     * @param clazz
     * @param itemName
     * @return
     */
    private static Field tryGetFieldWithoutExp(Class<?> clazz, String itemName) {

        try {

            return clazz.getDeclaredField(itemName);

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取属性设置属性
     *
     * @param clazz
     * @param field
     * @return
     */
    private static <T> Method tryGetSetMethod(Class<T> clazz, Field field, String methodName) {

        try {
            return clazz.getDeclaredMethod(methodName, field.getType());
        } catch (Exception e) {

            return null;
        }

    }
}
