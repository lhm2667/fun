package com.lhm.self.fun.currentlimit;



 /**
 * @Author lihaiming
 * @Description //TODO  限流类型枚举类
 * @Date      2020/4/9 10:47
 * @Param
 * @return
 **/
public enum LimitType {
    /**
     * 自定义key
     */
    CUSTOMER,

    /**
     * 请求者IP
     */
    IP;
}
