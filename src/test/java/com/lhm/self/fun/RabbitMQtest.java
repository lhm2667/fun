package com.lhm.self.fun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lhm.self.fun.rabbitAdmin.Cat;
import com.lhm.self.fun.rabbitAdmin.Packaged;
import com.lhm.self.fun.test.test;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.Exceptions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author lihaiming
 * @ClassName: RabbitMQtest
 * @Description: TODO
 * @date 2020/4/1514:12
 */
@SpringBootTest
public class RabbitMQtest {

    @Autowired
    RabbitAdmin rabbitAdmin;


    @Test
    public void testAdimin() throws Exception {
        //直连监听
        rabbitAdmin.declareExchange(new DirectExchange("test.dirct", false, false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic", false, false));
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout", false, false));
        rabbitAdmin.declareQueue(new Queue("test.dirct.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue", false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue", false));
        //第一个参数：具体的队列 第二个参数：绑定的类型 第三个参数：交换机 第四个参数：路由key 第五个参数：arguments 参数
        rabbitAdmin.declareBinding(new Binding("test.dirct.queue", Binding.DestinationType.QUEUE,
                "test.dirct", "direct", new HashMap<>()));
        //BindingBuilder 链式编程
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(new Queue("test.topic.queue", false))
                        .to(new TopicExchange("test.topic", false, false))
                        .with("user.#"));
        rabbitAdmin.declareBinding(
                BindingBuilder.bind(new Queue("test.fanout.queue", false))
                        .to(new FanoutExchange("test.fanout", false, false)));
        //清空队列数据
        rabbitAdmin.purgeQueue("test.topic.queue", false);

    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage() throws Exception {
        //1创建消息
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc", "信息描述..");
        messageProperties.getHeaders().put("type", "自定义消息类型..");
        //消息体
        Message message = new Message("Hello world!".getBytes(), messageProperties);
        //转换并发送
        //MessagePostProcessor 在消息发送完毕后再做一次转换进行再加工，匿名接口，需要重写方法
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.err.println("------添加额外的设置---------");
                message.getMessageProperties().getHeaders().put("desc", "额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr", "额外新加的属性");
                return message;
            }
        });
    }

    @Test
    public void testSendMessage2() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message("mq 消息1234".getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.abc", message);
        rabbitTemplate.convertAndSend("topic001", "spring.amqp", "hello object message send!");
        rabbitTemplate.convertAndSend("topic002", "rabbit.abc", "hello object message send!");

    }

    @Test
    public void testSendMessage3() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("text/plain");
        Message message = new Message("mq 消息1234".getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.abc", message);
        rabbitTemplate.send("topic002", "rabbit.abc", message);
    }

    @Test
    public void testSendJsonMessage() throws Exception {
        Cat cat = new Cat();
        cat.setAge(11);
        cat.setName("miaomiao");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cat);
        System.err.println("cat 4 json: " + json);
        MessageProperties messageProperties = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties.setContentType("application/json");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.order", message);
    }

    @Test
    public void testSendJsonMessage2() throws Exception {
        Cat cat = new Cat();
        cat.setAge(11);
        cat.setName("miaomiao");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(cat);
        System.err.println("cat 4 json: " + json);
        MessageProperties messageProperties = new MessageProperties();
        //这里注意一定要修改contentType为 application/json
        messageProperties.setContentType("application/json");
        messageProperties.setHeader("__TypeId__", "com.lhm.self.fun.rabbitAdmin.Cat");
        Message message = new Message(json.getBytes(), messageProperties);
        rabbitTemplate.send("topic001", "spring.cat", message);
    }

    @Test
    public void testSendMappingMessage()throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        Cat cat = new Cat();
        cat.setAge(100);
        cat.setName("小猫");
        String json1 = mapper.writeValueAsString(cat);
        System.err.println("cat 4 json: " + json1);
        MessageProperties messageProperties1 = new MessageProperties();
        messageProperties1.setContentType("application/json");
        //设置的是标签，而不是全路径
        messageProperties1.getHeaders().put("__TypeId__","cat");
        Message message1 = new Message(json1.getBytes(),messageProperties1);
        rabbitTemplate.send("topic001", "spring.order",message1);

        Packaged packaged = new Packaged();
        packaged.setId("1");
        packaged.setName("A");
        packaged.setDescription("描述");
        String json2 = mapper.writeValueAsString(packaged);
        System.err.println("packaged 4 json: " + json2);
        MessageProperties messageProperties2 = new MessageProperties();
        messageProperties2.setContentType("application/json");
        messageProperties2.getHeaders().put("__TypeId__","packaged");
        Message message2 = new Message(json2.getBytes(),messageProperties2);
        rabbitTemplate.send("topic001", "spring.pack", message2);

    }


    @Test
    public void  testSendExtConverterMessage() throws Exception{
       // byte[] body = Files.readAllBytes(Paths.get("d:/002_books", "picture.png"));
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setContentType("image/png");
//        messageProperties.getHeaders().put("extName", "png");
//        Message message = new Message(body, messageProperties);
//        rabbitTemplate.send("", "image_queue", message);
        byte[] body = Files.readAllBytes(Paths.get("E:/谷歌下载", "资金超预算.pdf"));
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/pdf");
        Message message = new Message(body, messageProperties);
        rabbitTemplate.send("topic001", "pdf_queue", message);
    }

}
