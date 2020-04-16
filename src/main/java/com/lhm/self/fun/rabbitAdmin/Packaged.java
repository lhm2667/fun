package com.lhm.self.fun.rabbitAdmin;

import java.io.Serializable;

/**
 * @author lihaiming
 * @ClassName: Packaged
 * @Description: TODO
 * @date 2020/4/1610:21
 */
public class Packaged implements Serializable {

    private static final Long serialVersionUID = 1L;
    private String id;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
