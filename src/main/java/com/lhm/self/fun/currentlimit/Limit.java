package com.lhm.self.fun.currentlimit;


import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.*;

/**
 * @Author lihaiming
 * @Description //TODO
 * @Date      2020/4/9 10:49
 * @Param 我们自定义个@Limit注解，注解类型为ElementType.METHOD即作用于方法上。
  * period表示请求限制时间段，count表示在period这个时间段内允许放行请求的次数。
  * limitType代表限流的类型，可以根据请求的IP、自定义key，如果不传limitType属性则默认用方法名作为默认key。
 * @return
 **/
 @Target({ElementType.METHOD,ElementType.TYPE})
 @Retention(RetentionPolicy.RUNTIME)
 @Inherited
 @Document
public @interface Limit {

    /**
     * 名字
     */
    String name() default "";

    /**
     * key
     */
    String key() default "";

    /**
     * Key的前缀
     */
    String prefix() default "";

    /**
     * 给定的时间范围 单位(秒)
     */
    int period();

    /**
     * 一定时间内最多访问次数
     */
    int count();

    /**
     * 限流的类型(用户自定义key 或者 请求ip)
     */
    LimitType limitType() default LimitType.CUSTOMER;

}
