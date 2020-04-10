package com.lhm.self.fun.dynamicproxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lihaiming
 * @ClassName: TargetInvoker
 * @Description: TODO 在InvocationHandler#invoker中必须调用目标类被代理的方法，
 * 否则无法做到代理的实现。下面为实现 InvocationHandler 的代码。
 * @date 2020/4/1010:12
 */
public class TargetInvoker implements InvocationHandler {
    // 代理中持有的目标类
    private Object target;
    public TargetInvoker(Object target) {
        this.target = target;
    }


     /**
     * @Author lihaiming
     * @Description //TODO
     * @Date      2020/4/10 10:16
     * @Param proxy 代理目标对象的代理对象，它是真实的代理对象。
      * method 执行目标类的方法
      * args 执行目标类的方法的参数
     * @return
     **/
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk 代理执行前");
        Object result = method.invoke(target,args);
        System.out.println("jdk 代理执行后");
        return result ;
    }
}
