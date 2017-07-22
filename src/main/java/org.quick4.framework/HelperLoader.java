package org.quick4.framework;

import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IocHelper;
import org.smart4j.framework.utils.ClassUtil;

/**
 * 加载对应的Helper类
 */
public final class HelperLoader {

    //加载
    public static void init(){
        Class<?>[] classList = {ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        for (Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
