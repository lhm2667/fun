package com.lhm.self.fun.dynamicproxy.domain;

import java.io.Serializable;

/**
 * @author lihaiming
 * @ClassName: Cat
 * @Description: TODO
 * @date 2020/4/1010:04
 */
public class Cat implements Animal , Serializable {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void call() {
        System.out.println("喵喵喵 ~");
    }

    public void hobby(){
        System.out.println("fish ~");
    }
}
