package com.lhm.self.fun.dynamicproxy.cglibproxy;

import com.lhm.self.fun.dynamicproxy.domain.Animal;
import com.lhm.self.fun.dynamicproxy.domain.Cat;

/**
 * @author lihaiming
 * @ClassName: Main
 * @Description: TODO
 * @date 2020/4/1010:53
 */
public class Main {

    /*创建 CGLIB 动态代理类使用 net.sf.cglib.proxy.Enhancer
     类进行创建，它是 CGLIB 动态代理中的核心类，首先创建个简单的代理类：*/
    public static void main(String[] args) {
        Cat cat = (Cat) CglibProxy.getProxy(Cat.class);
        cat.call();
        Cat cat2 = (Cat) CglibProxy2.getProxy(Cat.class);
        cat2.call();
        cat2.hobby();

    }
}
