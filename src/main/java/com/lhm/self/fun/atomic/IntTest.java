package com.lhm.self.fun.atomic;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author lihaiming
 * @ClassName: IntTest
 * @Description: TODO
 * @date 2020/4/914:44
 */
public class IntTest {
    static int number = 0;
    static AtomicInteger num = new AtomicInteger(0);
    static BigDecimal bigDecimalNum = BigDecimal.ZERO;
    //实现CAS
    static AtomicReference<BigDecimal> atomicDecimalNum = new AtomicReference<BigDecimal>(BigDecimal.ZERO);


    public static void main(String[] args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    number = number + 1;
                    num.incrementAndGet();
                    bigDecimalNum = bigDecimalNum.add(new BigDecimal(1));
                    //手动实现CAS
                    while (true) {
                        BigDecimal pre = atomicDecimalNum.get();
                        BigDecimal next = pre.add(BigDecimal.ONE);
                        //是通过值比较来知晓是不是能够更新成功，那如果线程1先加1再减1，
                        // 这样主内存还是原来的值，即线程2还是可以更新成功的  有缺陷  testCAS方法
                        if (atomicDecimalNum.compareAndSet(pre, next)) {
                            break;
                        }
                    }

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
        System.out.println(bigDecimalNum);   //输出不是200000  说明线程不安全
        System.out.println(atomicDecimalNum);   //输出是200000 线程安全
        //测试CAS
        testCAS();
        //测试AtomicStampedReference
        tsetStampedCAS();
    }

    //值比较的CAS 不准确 有可能加一又减掉一
    public static void testCAS() throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = num.get();
                System.out.println("开始number:" + a);
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(num.compareAndSet(a, a++));


            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始增加操作");
                int a = num.incrementAndGet();
                System.out.println("当前number:" + a);
                int b = num.decrementAndGet();
                System.out.println("当前number:" + b);
            }
        });
        t2.start();

        t1.join();
        t2.join();
    }

    static AtomicStampedReference<Integer> numberStamped = new AtomicStampedReference<Integer>(0, 0);


     /**
     * @Author lihaiming
     * @Description //TODO  使用AtomicStampedReference，为其添加一个版本号。线程1在刚开始读取主内存的时候，获取到值为0，版本为1，
      * 线程2也获取到这两个值，线程1进行加1，减1的操作的时候，版本各加1，现在主内存的值为0，版本为2，而线程2还拿着预计值为0，
      * 版本为1的数据尝试写入主内存，这个时候因版本不同而更新失败。具体我们用代码试下：
      *
     * @Date      2020/4/9 15:42
     * @Param
     * @return
     **/
    public static void tsetStampedCAS() throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int a = numberStamped.getReference();
                int s = numberStamped.getStamp();
                System.out.println("开始number:" + a + ",stamp:" + s);
                try {
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(numberStamped.compareAndSet(a, a + 1, s, s + 1));

            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("开始增加操作");
                int a = numberStamped.getReference();
                int s = numberStamped.getStamp();
                numberStamped.compareAndSet(a, a + 1, s, s + 1);
                System.out.println("当前number:" + a + ",stamp:" + (s + 1));
                a = numberStamped.getReference();
                s = numberStamped.getStamp();
                numberStamped.compareAndSet(a, a - 1, s, s + 1);
                System.out.println("当前number:" + a + ",stamp:" + (s + 1));
            }
        });
        t2.start();
        t1.join();
        t2.join();

    }

}
