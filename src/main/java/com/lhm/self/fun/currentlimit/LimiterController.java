package com.lhm.self.fun.currentlimit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lihaiming
 * @ClassName: LimiterController
 * @Description: TODO
 * 我们将@Limit注解作用在需要进行限流的接口方法上，下边我们给方法设置@Limit注解，
 * 在10秒内只允许放行3个请求，这里为直观一点用AtomicInteger计数。
 * @date 2020/4/913:14
 */
@RestController
@RequestMapping("limit")
public class LimiterController {
    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();

    @Limit(key = "limitTest",period = 10,count = 3)
    @GetMapping("limitTest1")
    public int TestLimit1(){
      return   ATOMIC_INTEGER_1.incrementAndGet();
    }

    @Limit(key = "customer_limit_test",period = 10,count = 3,limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {
        return ATOMIC_INTEGER_2.incrementAndGet();
    }
    @Limit(key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {

        return ATOMIC_INTEGER_3.incrementAndGet();
    }


}
