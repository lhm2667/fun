package com.lhm.self.fun.dynamicproxy.cglibproxy;

import org.springframework.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;


/**
 * @author lihaiming
 * @ClassName: TargetCallbackFilter
 * @Description: TODO
 * @date 2020/4/1010:56
 */
public class TargetCallbackFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if ("hobby".equals(method.getName())) {
            return 1;
        } else {
            return 0;
        }
    }
}
