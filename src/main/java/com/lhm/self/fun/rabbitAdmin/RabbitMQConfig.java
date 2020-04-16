package com.lhm.self.fun.rabbitAdmin;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author lihaiming
 * @ClassName: RabbitMQConfig
 * @Description: TODO
 * @date 2020/4/1513:49
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses("127.0.0.1:5672");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        //connectionFactory.setVirtualHost("/vhost_cp");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     * HeadersExchange ：通过添加属性key-value匹配
     * DirectExchange:按照routingkey分发到指定队列
     * TopicExchange:多关键字匹配
     */
    @Bean
    public TopicExchange exchange001() {
        return new TopicExchange("topic001", true, false);
    }

    @Bean
    public Queue queue001() {
        return new Queue("queue001", true); //队列持久
    }

    @Bean
    public Binding binding001() {
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002() {
        return new TopicExchange("topic002", true, false);
    }

    @Bean
    public Queue queue002() {
        return new Queue("queue002", true); //队列持久
    }

    @Bean
    public Binding binding002() {
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003() {
        return new Queue("queue003", true); //队列持久
    }

    @Bean
    public Binding binding003() {
        //同一个Exchange绑定了2个队列
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    public Queue queue_image() {
        return new Queue("image_queue", true); //队列持久
    }

    @Bean
    public Queue queue_pdf() {
        return new Queue("pdf_queue", true); //队列持久
    }

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        //添加多个队列进行监听
        container.setQueues(queue001(), queue002(), queue003());
        //当前消费者数量
        container.setConcurrentConsumers(1);
        ///最大消费者数量
        container.setMaxConcurrentConsumers(5);
        //设置重回队列，一般设置false
        container.setDefaultRequeueRejected(false);
        //设置自动签收机制
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //设置listener外露
        container.setExposeListenerChannel(true);
        //消费端标签生成策略
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                //每个消费端都有自己独立的标签
                return queue + "_" + UUID.randomUUID().toString();
            }
        });
       /* //消息监听
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String msg = new String(message.getBody());
                System.err.println("----------消费者: " + msg);
            }
        });*/

        //使用适配器
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageDelegate());
        messageListenerAdapter.setDefaultListenerMethod("consumeMessage");
        //全局的转换器:所有小的Converter都可以放到这个大的Converter中
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter();

        TextMessageConverter textConvert = new TextMessageConverter();
        //text走文本转换器
        converter.addDelegate("text", textConvert);
        converter.addDelegate("html/text", textConvert);
        converter.addDelegate("xml/text", textConvert);
        converter.addDelegate("text/plain", textConvert);

        //json走json转换器
        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        converter.addDelegate("json", jsonConvert);
        converter.addDelegate("application/json", jsonConvert);
        //图片走图片转换器
        ImageMessageConverter imageConverter = new ImageMessageConverter();
        converter.addDelegate("image/png", imageConverter);
        converter.addDelegate("image", imageConverter);
        //pdf走pdf转换器
        PDFMessageConverter pdfConverter = new PDFMessageConverter();
        converter.addDelegate("application/pdf", pdfConverter);
        messageListenerAdapter.setMessageConverter(converter);
        container.setMessageListener(messageListenerAdapter);

    /*    ///重点，加入json格式的转换器 json对应Map对象  MessageDelegate 对应map
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        //需要将javaTypeMapper放入到Jackson2JsonMessageConverter对象中
        *//*在SpringBoot 2.0.0版本时，DefaultClassMapper类源码构造函数进行了修改，
        不再信任全部package而是仅仅信任java.util、java.lang。*//*
        RabbitMqFastJsonClassMapper defaultJackson2JavaTypeMapper = new RabbitMqFastJsonClassMapper();
        //添加支持java对象多映射转换
        Map<String, Class<?>> idClassMappering = new HashMap<String, Class<?>>();
        idClassMappering.put("cat", com.lhm.self.fun.rabbitAdmin.Cat.class);
        idClassMappering.put("packaged", com.lhm.self.fun.rabbitAdmin.Packaged.class);
        defaultJackson2JavaTypeMapper.setIdClassMapping(idClassMappering);
        jackson2JsonMessageConverter.setJavaTypeMapper(defaultJackson2JavaTypeMapper);
        messageListenerAdapter.setMessageConverter(jackson2JsonMessageConverter);
        container.setMessageListener(messageListenerAdapter);
*/
       /* // 2 适配器方式: 我们的队列名称 和 方法名称 也可以进行一一的匹配
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageDelegate());
        messageListenerAdapter.setMessageConverter(new TextMessageConverter());
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("queue001", "method1");
        queueOrTagToMethodName.put("queue002", "method2");
        messageListenerAdapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        container.setMessageListener(messageListenerAdapter);*/
        return container;
    }


}
