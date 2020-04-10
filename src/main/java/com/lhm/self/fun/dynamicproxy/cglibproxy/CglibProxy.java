package com.lhm.self.fun.dynamicproxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * @author lihaiming
 * @ClassName: CglibProxy
 * @Description: TODO
 * @date 2020/4/1010:50
 */
public class CglibProxy {
    public static Object getProxy(Class<?> calzz){
        Enhancer enhancer = new Enhancer();
        // 设置类加载
        enhancer.setClassLoader(calzz.getClassLoader());
        // 设置被代理类
        enhancer.setSuperclass(calzz);
        // 设置方法拦截器
        enhancer.setCallback(new TargetInterceptor());
        // 创建代理类
        return enhancer.create();
    }
}
