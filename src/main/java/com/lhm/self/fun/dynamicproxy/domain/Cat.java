package com.lhm.self.fun.dynamicproxy.domain;

/**
 * @author lihaiming
 * @ClassName: Cat
 * @Description: TODO
 * @date 2020/4/1010:04
 */
public class Cat implements Animal{
    @Override
    public void call() {
        System.out.println("喵喵喵 ~");
    }

    public void hobby(){
        System.out.println("fish ~");
    }
}
