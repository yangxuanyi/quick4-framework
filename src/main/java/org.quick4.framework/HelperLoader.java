package org.quick4.framework;

import org.quick4.framework.helper.BeanHelper;
import org.quick4.framework.helper.ClassHelper;
import org.quick4.framework.helper.ControllerHelper;
import org.quick4.framework.helper.IocHelper;
import org.quick4.framework.utils.ClassUtil;

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
