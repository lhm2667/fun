package com.lhm.self.fun.rabbitAdmin;


import java.io.File;
import java.util.Map;

/**
 * @author lihaiming
 * @ClassName: MessageDelegate
 * @Description: TODO 自定义适配器
 * @date 2020/4/1516:07
 */
public class MessageDelegate {
    public void consumeMessage(byte[] messageBody) {
        System.err.println("字节数组方法, 消息内容:" + new String(messageBody));
    }

   public void consumeMessage(String messageBody) {
        System.err.println("默认方法, 消息内容:" + messageBody);
    }
    //json对应Map对象
    public void consumeMessage(Cat cat) {
        System.err.println("cat对象, 消息内容, id: " + cat.getAge() +
                ", name: " + cat.getName() +
                ", age: "+ cat.getAge());
    }

    public void consumeMessage(Packaged pack) {
        System.err.println("package对象, 消息内容, id: " + pack.getId() +
                ", name: " + pack.getName() +
                ", content: "+ pack.getDescription());
    }
    public void method1(String messageBody) {
        System.err.println("method1 收到消息内容:" + new String(messageBody));
    }

    public void method2(String messageBody) {
        System.err.println("method2 收到消息内容:" + new String(messageBody));
    }

    public void consumeMessage(File file) {
        System.err.println("文件对象 方法, 消息内容:" + file.getName());
    }
    //json对应Map对象
    public void consumeMessage(Map messageBody) {
        System.err.println("map方法, 消息内容:" + messageBody);
    }

    public void handleMessage(byte[] messageBody) {
        System.err.println("默认方法, 消息内容:" + new String(messageBody));
    }


}
