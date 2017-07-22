package org.quick4.framework.helper;

import org.quick4.framework.utils.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 */
public final class BeanHelper {

    /**
     * 定义bean映射（用于存放bean类与bean实例的映射关系）
     */
    private  static final Map<Class<?> , Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass,obj);
        }
    }


    /**
     * 获取bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    public static <T> T getBean(Class<?> cls){
        if(!BEAN_MAP.containsKey(cls)){
            new RuntimeException("can not getbean by class:" + cls);
        }

        return (T) BEAN_MAP.get(cls);
    }
}

