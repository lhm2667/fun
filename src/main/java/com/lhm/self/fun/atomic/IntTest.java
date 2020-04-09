package com.lhm.self.fun.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lihaiming
 * @ClassName: IntTest
 * @Description: TODO
 * @date 2020/4/914:44
 */
public class IntTest {
    static int number = 0;
    static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    number = number + 1;
                    num.incrementAndGet();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        thread1.start();
        ;
        Thread thread2 = new Thread(runnable);
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(number); //输出不是200000  说明线程不安全
        System.out.println(num);   //输出是200000 线程安全

    }

}
