package com.lhm.self.fun.dynamicproxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author lihaiming
 * @ClassName: TargetInterceptor2
 * @Description: TODO
 * @date 2020/4/1010:55
 */
public class TargetInterceptor2 implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("CGLIB 调用前 TargetInterceptor2");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("CGLIB 调用后 TargetInterceptor2");
        return result;
    }
}
