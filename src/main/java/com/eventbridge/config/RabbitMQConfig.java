package com.eventbridge.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// config/RabbitMQConfig.java
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

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}


