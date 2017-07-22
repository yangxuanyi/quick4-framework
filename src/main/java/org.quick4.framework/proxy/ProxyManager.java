package org.quick4.framework.proxy;

/**
 * 代理管理器
 */
public class ProxyManager {

    /*public static <T> T createProxy(final Class<?> targetClass,
                                    final List<Proxy> proxyList){
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {

            public Object intercept(Object targetObject, Method targetMethod,
                                    Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass,targetObject,
                        targetMethod,methodProxy,methodParams,proxyList).doProxyChain();
            }
        });
    }*/
}
