package com.lhm.self.fun.dynamicproxy.staticproxy;

import com.lhm.self.fun.dynamicproxy.domain.Animal;

/**
 * @author lihaiming
 * @ClassName: StaticProxyAnimal
 * @Description: TODO
 * @date 2020/4/1010:06
 */
public class StaticProxyAnimal implements Animal {

    private Animal impl;
    public StaticProxyAnimal(Animal impl) {
        this.impl = impl;
    }
    @Override
    public void call() {
        System.out.println("猫饥饿");
        impl.call();
    }
}
