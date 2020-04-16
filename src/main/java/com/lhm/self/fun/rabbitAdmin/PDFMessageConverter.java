package com.lhm.self.fun.rabbitAdmin;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author lihaiming
 * @ClassName: PDFMessageConverter
 * @Description: TODO
 * @date 2020/4/1610:56
 */
public class PDFMessageConverter implements MessageConverter {
    @Override
    public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
        throw new MessageConversionException(" convert error ! ");
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        System.err.println("-----------PDF MessageConverter----------");
        byte[] body = message.getBody();
        String fileName = UUID.randomUUID().toString();
        String path = "e:/010_test/" + fileName + ".pdf";
        File f = new File(path);
        try {
            Files.copy(new ByteArrayInputStream(body), f.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}
