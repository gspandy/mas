package com.letv.mas.caller.iptv.tvproxy.common.util;

import com.letv.mas.caller.iptv.tvproxy.common.annotation.CopyFieldAnnotation;
import com.letv.mas.caller.iptv.tvproxy.common.model.dao.db.table.AlbumMysqlTable;
import com.letv.mas.caller.iptv.tvproxy.common.plugin.LetvCommonException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class ParseUtil {
    private static final Log logger = LogFactory.getLog(ParseUtil.class);

    public static <E> E copyBeanByResultField(Class<E> resultClass, Object param) throws Exception {
        E result = null;
        try {
            result = (E) Class.forName(resultClass.getName()).newInstance();
        } catch (Exception e) {
            throw new LetvCommonException("init error", "init " + resultClass.getName() + "newInstance is error", e);
        }
        return copyBeanByResultField(resultClass, result, param);
    }

    public static <E> E copyBeanByResultField(Class<E> resultClass, E result, Object param) throws Exception {
        if (param == null) {
            logger.debug("param is null");
            return null;
        }
        if (result == null) {
            try {
                result = (E) Class.forName(resultClass.getName()).newInstance();
            } catch (Exception e) {
                throw new LetvCommonException("init error", "init " + result.getClass().getName()
                        + "newInstance is error", e);
            }
        }
        Field[] resultFields = result.getClass().getDeclaredFields();
        if (resultFields == null || resultFields.length == 0) {
            throw new LetvCommonException(result.getClass().getName() + " fields is null");
        }
        Class paramsClass = param.getClass();
        Field[] paramFields = paramsClass.getDeclaredFields();
        if (paramFields == null || paramFields.length == 0) {
            throw new LetvCommonException(paramsClass.getName() + " fields is null");
        }
        for (Field resultField : resultFields) {
            resultField.setAccessible(true);
            String propertyName = resultField.getName();
            if (propertyName.startsWith("serial")) {
                continue;
            }
            CopyFieldAnnotation annotation = resultField.getAnnotation(CopyFieldAnnotation.class);
            if (annotation == null) {
                copyBeanPropertyValue(result, getBeanPropertyValue(param, propertyName), propertyName);
            } else {
                if (!annotation.isCopy()) {
                    logger.debug(propertyName + " isCopy is false");
                    continue;
                }
                copyBeanPropertyValue(result, getBeanPropertyValue(param, annotation.value()), propertyName);
            }
        }

        return result;
    }

    public static <E> E copyBeanByParamsField(Class<E> resultClass, Object param) throws Exception {
        E result = null;
        try {
            result = (E) Class.forName(resultClass.getName()).newInstance();
        } catch (Exception e) {
            throw new LetvCommonException("init error", "init " + resultClass.getName() + "newInstance is error", e);
        }
        return copyBeanByParamsField(resultClass, result, param);
    }

    public static <E> E copyBeanByParamsField(Class<E> resultClass, E result, Object param) throws Exception {
        String logPrefix = "copyBeanByParamsField_" + resultClass.getName();
        if (param == null) {
            logger.debug(logPrefix + "param is null");
            return null;
        }
        if (result == null) {
            try {
                result = (E) Class.forName(resultClass.getName()).newInstance();
            } catch (Exception e) {
                throw new LetvCommonException("init error", "init " + result.getClass().getName()
                        + "newInstance is error", e);
            }
        }
        Field[] resultFields = result.getClass().getDeclaredFields();
        if (resultFields == null || resultFields.length == 0) {
            throw new LetvCommonException(result.getClass().getName() + " fields is null");
        }
        Class paramsClass = param.getClass();
        Field[] paramFields = paramsClass.getDeclaredFields();
        if (paramFields == null || paramFields.length == 0) {
            throw new LetvCommonException(paramsClass.getName() + " fields is null");
        }
        for (Field paramField : paramFields) {
            paramField.setAccessible(true);
            String propertyName = paramField.getName();
            if (propertyName.startsWith("serial")) {
                continue;
            }
            Object value = paramField.get(param);
            if (value == null) {
                logger.debug(logPrefix + "_" + propertyName + " value is null");
                // 2015-11-23注释掉，对于原数据有值，但新数据为Null的情况，需要清掉原数据的值
                // continue;
            }
            CopyFieldAnnotation annotation = paramField.getAnnotation(CopyFieldAnnotation.class);
            if (annotation == null) {
                copyBeanPropertyValue(result, value, propertyName);
            } else {
                if (!annotation.isCopy()) {
                    logger.debug(logPrefix + "_" + propertyName + " isCopy is false");
                    continue;
                }
                Class<?> paramFieldType = paramField.getType();
                if (paramFieldType.isPrimitive() || !annotation.isSplit()) {// 普通数据类型复制或完整复制
                    String name = annotation.value() != null && annotation.value().trim().length() > 0 ? annotation
                            .value() : propertyName;
                    copyBeanPropertyValue(result, value, name);
                } else if (Map.class.isAssignableFrom(paramFieldType)) {// map数据类型复制
                    Map<Object, Object> paramValue = (Map<Object, Object>) value;
                    if (paramValue == null || paramValue.size() == 0) {
                        logger.debug(logPrefix + "_" + propertyName + " value is null");
                    }
                    if (annotation.valueToString()) {
                        StringBuffer keys = new StringBuffer();
                        StringBuffer values = new StringBuffer();

                        if (paramValue != null) {
                            for (Object key : paramValue.keySet()) {
                                keys.append(key).append(",");
                                if (paramValue.get(key) != null) {
                                    values.append(paramValue.get(key)).append(",");
                                }
                            }
                            if (keys.length() > 0) {
                                keys = keys.deleteCharAt(keys.length() - 1);
                            }
                            if (values.length() > 0) {
                                values = values.deleteCharAt(values.length() - 1);
                            }
                        }

                        String keyName = annotation.mapKey() != null && annotation.mapKey().trim().length() > 0 ? annotation
                                .mapKey() : propertyName;
                        copyBeanPropertyValue(result, keys.toString(), keyName);
                        logger.debug(logPrefix + "_" + keyName + "_" + keys.toString());

                        String valueName = annotation.mapValue() != null && annotation.mapValue().trim().length() > 0 ? annotation
                                .mapValue() : propertyName;
                        copyBeanPropertyValue(result, values.toString(), valueName);
                        logger.debug(logPrefix + "_" + valueName + "_" + values.toString());
                    } else {
                        if (paramValue != null) {
                            for (Object key : paramValue.keySet()) {
                                String keyName = annotation.mapKey() != null && annotation.mapKey().trim().length() > 0 ? annotation
                                        .mapKey() : propertyName;
                                copyBeanPropertyValue(result, key, keyName);
                                if (paramValue.get(key) != null) {
                                    String valueName = annotation.mapValue() != null
                                            && annotation.mapValue().trim().length() > 0 ? annotation.mapValue()
                                            : propertyName;
                                    copyBeanPropertyValue(result, paramValue.get(key), valueName);
                                }
                            }
                        } else {
                            // 如果源数据返回空，则清空现有数据
                            String keyName = annotation.mapKey() != null && annotation.mapKey().trim().length() > 0 ? annotation
                                    .mapKey() : propertyName;
                            copyBeanPropertyValue(result, null, keyName);
                            String valueName = annotation.mapValue() != null
                                    && annotation.mapValue().trim().length() > 0 ? annotation.mapValue() : propertyName;
                            copyBeanPropertyValue(result, null, valueName);
                        }
                    }
                } else if (paramFieldType.isArray()) {// 数组类型
                    Object[] paramValue = (Object[]) value;
                    if (annotation.valueToString()) {
                        StringBuffer values = new StringBuffer();
                        if (paramValue != null) {
                            for (Object object : paramValue) {
                                if (object != null) {
                                    values.append(object).append(",");
                                }
                            }
                            if (values.length() > 0) {
                                values = values.deleteCharAt(values.length() - 1);
                            }
                        }
                        String name = annotation.value() != null && annotation.value().trim().length() > 0 ? annotation
                                .value() : propertyName;
                        copyBeanPropertyValue(result, values.toString(), name);
                    } else {
                        String name = annotation.value() != null && annotation.value().trim().length() > 0 ? annotation
                                .value() : propertyName;
                        if (paramValue != null) {
                            for (Object object : paramValue) {
                                copyBeanPropertyValue(result, object, name);
                            }
                        } else {
                            copyBeanPropertyValue(result, null, name);
                        }
                    }
                } else if (Collection.class.isAssignableFrom(paramsClass)) {// 集合类型
                    Collection collection = (Collection) value;
                    if (annotation.valueToString()) {
                        StringBuffer values = new StringBuffer();
                        if (collection != null) {
                            Iterator iterator = collection.iterator();
                            while (iterator.hasNext()) {
                                Object object = iterator.next();
                                if (object != null) {
                                    values.append(object).append(",");
                                }
                            }
                            if (values.length() > 0) {
                                values = values.deleteCharAt(values.length() - 1);
                            }
                        }
                        String name = annotation.value() != null && annotation.value().trim().length() > 0 ? annotation
                                .value() : propertyName;
                        copyBeanPropertyValue(result, values.toString(), name);
                    } else {
                        String name = annotation.value() != null && annotation.value().trim().length() > 0 ? annotation
                                .value() : propertyName;
                        if (collection != null) {
                            Iterator iterator = collection.iterator();
                            while (iterator.hasNext()) {
                                Object object = iterator.next();
                                if (object != null) {
                                    copyBeanPropertyValue(result, object, name);
                                }
                            }
                        } else {
                            copyBeanPropertyValue(result, null, name);
                        }

                    }
                }
            }
        }
        return result;
    }

    private static Object getBeanPropertyValue(Object param, String propertyName) throws Exception {
        Field[] declaredFields = param.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (propertyName.equals(field.getName())) {
                return field.get(param);
            }
        }
        return null;
    }

    private static void copyBeanPropertyValue(Object result, Object value, String propertyName) throws Exception {
        Field[] declaredFields = result.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (propertyName.equals(field.getName())) {
                try {
                    field.set(result, value);
                } catch (Exception e) {
                    logger.error("copyBeanPropertyValue_" + propertyName + "_" + value + ": set value return error-", e);
                }
                break;
            }
        }
    }

    public static <E> List<E> parseListByResultField(Class<E> resultClass, List<?> param) {
        if (param != null) {
            List<E> result = new ArrayList<E>();
            for (Object obj : param) {
                try {
                    E tmp_e = copyBeanByResultField(resultClass, obj);
                    if (tmp_e != null) {
                        result.add(tmp_e);
                    }
                } catch (Exception e) {
                    logger.error("parseListByResultField exception,resultClass=" + resultClass + "," + e.getMessage(),
                            e);
                }
            }
            return result;
        }
        return null;
    }

    public static <E> List<E> parseListByParamField(Class<E> resultClass, List<?> param) {
        if (param != null) {
            List<E> result = new ArrayList<E>();
            for (Object obj : param) {
                try {
                    E tmp_e = copyBeanByParamsField(resultClass, obj);
                    if (tmp_e != null) {
                        result.add(tmp_e);
                    }
                } catch (Exception e) {
                    logger.error("parseListByParamField exception,resultClass=" + resultClass + "," + e.getMessage(), e);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 将list中对象转换
     * @param resultClass
     * @param paramClass
     * @param param
     * @return
     */
    public static <E> List<E> parseListByConstructor(Class<E> resultClass, Class<?> paramClass, List<?> param) {
        if (param != null) {
            List<E> result = new ArrayList<E>();
            for (Object obj : param) {
                E tmp_e = parseObjectByConstructor(resultClass, new Class[] { paramClass }, new Object[] { obj });
                if (tmp_e != null) {
                    result.add(tmp_e);
                }
            }
            return result;
        }
        return null;
    }

    /**
     * 通过构造器进行转换bean
     * @param resultClass
     *            结果class
     * @param classTypes
     *            参数数组
     * @param param
     * @return
     */
    public static <E> E parseObjectByConstructor(Class<E> resultClass, Class<?>[] classTypes, Object[] param) {
        try {
            Constructor<E> constructor = resultClass.getConstructor(classTypes);
            E e = constructor.newInstance(param);
            return e;
        } catch (Exception e) {
            logger.error("parseObjectByConstructor exception,resultClass=" + resultClass + "," + e.getMessage(), e);
        }
        return null;

    }

    /**
     * 将排行榜对象转换
     * @param ratingAndPlayRankItems
     * @param resultClass
     * @param page
     * @param pageSize
     * @return
     */
    public static <E> PageControl<E> parsePlayRankToPageControlByConstructor(
            List<AlbumMysqlTable> ratingAndPlayRankItems, Class<E> resultClass, Integer page, Integer pageSize) {
        if (ratingAndPlayRankItems != null && ratingAndPlayRankItems.size() > 0) {
            PageControl<E> pageControl = new PageControl<E>(pageSize, page);
            pageControl.setCount(ratingAndPlayRankItems.size());
            List<E> list = parseListByConstructor(resultClass, AlbumMysqlTable.class, ratingAndPlayRankItems);
            int size = list.size();
            if (size > (page - 1) * pageSize) {
                if (size < page * pageSize) {
                    list = list.subList((page - 1) * pageSize, list.size());
                } else {
                    list = list.subList((page - 1) * pageSize, page * pageSize);
                }
            } else {
                list.clear();
            }
            pageControl.setList(list);
            return pageControl;
        }
        return null;
    }
}
