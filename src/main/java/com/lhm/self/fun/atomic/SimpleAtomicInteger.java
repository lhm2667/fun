package com.lhm.self.fun.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lihaiming
 * @ClassName: SimpleAtomicInteger
 * @Description: TODO
 * @date 2020/4/914:58
 */
public class SimpleAtomicInteger {

    public static void main(String[] args) {
        //定义AtomicInteger类型的变量，值为1
        AtomicInteger i = new AtomicInteger(1);
        //incrementAndGet方法先新增1再返回，所以打印2，此时i为2
        System.out.println(i.incrementAndGet());
        //getAndIncrement方法先返回值再新增1，所以打印2，此时i为3
        System.out.println(i.getAndIncrement());
        //get方法返回当前i值，所以打印3，此时i为3
        System.out.println(i.get());
        //参数为正数即新增，getAndAdd方法先返回值再新增666，所以打印3，此时i为669
        System.out.println(i.getAndAdd(666));
        //参数为负数即减去，getAndAdd方法先返回值再减去1，所以打印669，此时i为668
        System.out.println(i.getAndAdd(-1));
        //参数为正数即新增，addAndGet方法先新增666再返回值，所以打印1334，此时i为1334
        System.out.println(i.addAndGet(666));
        //参数为负数即减去，addAndGet方法先减去-1再返回值，所以打印1333，此时i为1333
        System.out.println(i.addAndGet(-1));
        //getAndUpdate方法IntUnaryOperator参数是一个箭头函数，后面可以写任何操作，所以打印1333,此时i为13331
        System.out.println(i.getAndUpdate(x -> (x * 10 + 1)));
        //最终打印i为13331
        System.out.println(i.get());

    }
}
