package com.lhm.self.fun.dynamicproxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lihaiming
 * @ClassName: TargetInterceptor
 * @Description: TODO
 * @date 2020/4/1010:47
 */
public class TargetInterceptor implements MethodInterceptor {

    /*o 代理类对象
    method 当前被代理拦截的方法
    objects 拦截方法的参数
    methodProxy 代理类对应目标类的代理方法*/
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB 调用前");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("CGLIB 调用后");
        return result;
    }
}
