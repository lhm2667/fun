package com.lhm.self.fun.rabbitAdmin;

import java.io.Serializable;

/**
 * @author lihaiming
 * @ClassName: Cat
 * @Description: TODO
 * @date 2020/4/1517:18
 */
public class Cat  implements Serializable {
    private static final long serialVersionUID = 1L;
    int age;
    String name;

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
}
