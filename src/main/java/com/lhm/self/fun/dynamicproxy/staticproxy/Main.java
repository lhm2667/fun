package com.lhm.self.fun.dynamicproxy.staticproxy;

import com.lhm.self.fun.dynamicproxy.domain.Animal;
import com.lhm.self.fun.dynamicproxy.domain.Cat;

/**
 * @author lihaiming
 * @ClassName: Main
 * @Description: TODO
 * @date 2020/4/1010:07
 */
public class Main {

    /*静态代理就是在程序运行之前，代理类字节码.class就已编译好，通常一个静态代理类也只代理一个目标类，代理类和目标类都实现相同的接口。
     接下来就先通过 demo 进行分析什么是静态代理，当前创建一个 Animal 接口，里面包含call函数。
     创建目标类 Cat，同时实现 Animal 接口，下面是 Cat 发出叫声的实现。
     通过调用静态代理实现猫饥饿和叫行为。*/
    public static void main(String[] args) {
        Animal cat = new StaticProxyAnimal(new Cat());
        cat.call();
        //不使用代理
        Animal cat1 = new Cat();
        cat1.call();
    }

}
