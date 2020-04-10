package com.lhm.self.fun.dynamicproxy.jdkproxy;

import com.lhm.self.fun.dynamicproxy.domain.Animal;
import com.lhm.self.fun.dynamicproxy.domain.Cat;

/**
 * @author lihaiming
 * @ClassName: Main
 * @Description: TODO
 * @date 2020/4/1010:21
 */
public class Main {


    public static void main(String[] args) throws Exception {
        Cat cat = new Cat();
        Animal proxy = (Animal) DynamicProxyAnimal.getProxy(cat);
        proxy.call();
    }
}
