package org.quick4.framework.helper;

import org.quick4.framework.annotation.Action;
import org.quick4.framework.bean.Handler;
import org.quick4.framework.bean.Request;
import org.quick4.framework.utils.ArrayUtil;
import org.quick4.framework.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {

    /**
     * 用于存放请求和处理器的映射关系
     */
    private  static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有的controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            //遍历
            for (Class<?> controllerClass:controllerClassSet) {
                //获取Controller中定义方法
                Method[] methods = controllerClass.getDeclaredMethods();

                if (ArrayUtil.isNotEmpty(methods)){
                    //遍历Controller中的所有方法
                    for (Method method:methods){
                        //判断当前方法是否带有action注解
                        if(method.isAnnotationPresent(Action.class)){
                            //从Action获取url映射规则
                            Action action = method.getAnnotation(Action.class);
                            String mapping = action.value();
                            //验证url规则映射
                            if (mapping.matches("\\w+:/\\w*")){
                                String[] array = mapping.split(":");
                                if (ArrayUtil.isNotEmpty(array) && array.length ==2){
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    Request request = new Request(requestMethod,requestPath);
                                    Handler handler = new Handler(controllerClass,method);
                                    //初始化ACTION MAP
                                    ACTION_MAP.put(request,handler);
                                }
                            }

                        }
                    }
                }

            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }

}
