package com.lhm.self.fun.rabbitAdmin;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author lihaiming
 * @ClassName: TextMessageConverter
 * @Description: TODO
 * @date 2020/4/1516:20
 */
public class TextMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        return new Message(o.toString().getBytes(), messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        String contentType = message.getMessageProperties().getContentType();
        //对应的testSendMessage2()  messageProperties.setContentType("text/plain");
        if(null != contentType && contentType.contains("plain")) {  //"text" 也可以适配上
            return new String(message.getBody());
        }
        return message.getBody();
    }
}
