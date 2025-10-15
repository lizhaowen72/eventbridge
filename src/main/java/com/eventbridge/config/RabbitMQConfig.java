package com.eventbridge.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String DOMAIN_EVENTS_EXCHANGE = "domain-events-exchange";
    public static final String USER_EVENTS_QUEUE = "user-events-queue";
    public static final String ORDER_EVENTS_QUEUE = "order-events-queue";

    @Bean
    public TopicExchange domainEventsExchange() {
        return new TopicExchange(DOMAIN_EVENTS_EXCHANGE);
    }

    @Bean
    public Queue userEventsQueue() {
        return new Queue(USER_EVENTS_QUEUE, true);
    }

    @Bean
    public Queue orderEventsQueue() {
        return new Queue(ORDER_EVENTS_QUEUE, true);
    }

    @Bean
    public Binding userEventsBinding() {
        return BindingBuilder.bind(userEventsQueue())
                .to(domainEventsExchange())
                .with("user.*");
    }

    @Bean
    public Binding orderEventsBinding() {
        return BindingBuilder.bind(orderEventsQueue())
                .to(domainEventsExchange())
                .with("order.*");
    }

    /**
     * 为 HTTP 请求创建专门的 ObjectMapper（不启用类型信息）
     * 使用 @Primary 确保这是主要的 ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 注册 JavaTimeModule
        objectMapper.registerModule(new JavaTimeModule());

        // 禁用日期作为时间戳的序列化
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 忽略未知属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        System.out.println("✅ HTTP ObjectMapper 配置完成");
        return objectMapper;
    }

    /**
     * 为 RabbitMQ 创建专门的 ObjectMapper
     * 完全禁用多态类型处理，避免 @class 字段问题
     */
    @Bean("rabbitMQObjectMapper")
    public ObjectMapper rabbitMQObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // 注册 JavaTimeModule 以支持 Java 8 日期时间类型
        objectMapper.registerModule(new JavaTimeModule());

        // 禁用日期作为时间戳的序列化，使用 ISO-8601 格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // 完全禁用默认类型处理 - 这是关键！
        objectMapper.deactivateDefaultTyping();

        // 配置反序列化特性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        // 配置序列化特性
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        System.out.println("✅ RabbitMQ ObjectMapper 配置完成 - 多态类型处理已禁用");
        return objectMapper;
    }

    /**
     * 为 RabbitMQ 创建专门的 Jackson2JsonMessageConverter
     * 使用专门配置的 RabbitMQ ObjectMapper
     */
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(@Qualifier("rabbitMQObjectMapper") ObjectMapper rabbitMQObjectMapper) {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(rabbitMQObjectMapper);

        // 设置总是转换为推断的类型
        converter.setAlwaysConvertToInferredType(true);

        System.out.println("✅ RabbitMQ Jackson2JsonMessageConverter 配置完成");
        return converter;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        // 使用专门为 RabbitMQ 配置的消息转换器
        template.setMessageConverter(jsonMessageConverter);

        // 配置确认模式
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("✅ RabbitMQ 消息确认成功: " + (correlationData != null ? correlationData.getId() : "unknown"));
            } else {
                System.err.println("❌ RabbitMQ 消息确认失败: " + cause);
            }
        });

        // 配置返回模式
        template.setReturnsCallback(returned -> {
            System.err.println("❌ RabbitMQ 消息返回 - 无法路由: " + returned.getMessage());
            System.err.println("   回复代码: " + returned.getReplyCode());
            System.err.println("   回复文本: " + returned.getReplyText());
            System.err.println("   交换机: " + returned.getExchange());
            System.err.println("   路由键: " + returned.getRoutingKey());
        });

        System.out.println("✅ RabbitTemplate 配置完成");
        return template;
    }
}