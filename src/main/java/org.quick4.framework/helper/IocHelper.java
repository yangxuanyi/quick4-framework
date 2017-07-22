package org.quick4.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.quick4.framework.annotation.Inject;
import org.smart4j.framework.utils.CollectionUtil;
import org.smart4j.framework.utils.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {

    static {
        //获取bean类与bean实例之间的映射关系
        Map<Class<?> , Object > beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isEmpty(beanMap)){
           //遍历bean map
            for (Map.Entry<Class<?>,Object> beanEntry:beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                //获取bean类中定义的所有成员变量（bean field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)){
                    //遍历bean field
                    for (Field beanField : beanFields){
                        //判断当前bean field是否带有Intect注解
                        if (beanField.isAnnotationPresent(Inject.class)){
                            //在bean map中获取bean field 对应的实例
                            Class<?> beanFieldClass  = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null){
                                //通过反射初始化BeanField的值
                                ReflectionUtil.setField(beanInstance,beanField,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
