package com.lhm.self.fun.rabbitAdmin;

import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;

/**
 * @author lihaiming
 * @ClassName: RabbitMqFastJsonClassMapper
 * @Description: TODO  让 rabbit 信任全部package
 * @date 2020/4/1517:25
 */
public class RabbitMqFastJsonClassMapper extends DefaultJackson2JavaTypeMapper {
    /**
     * 构造函数初始化信任所有pakcage
     */
    public RabbitMqFastJsonClassMapper() {
        super();
        setTrustedPackages("*");
    }
}
