package com.eventbridge.command.infrastructure.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.config.RabbitMQConfig;

import java.nio.charset.StandardCharsets;

@Service
public class DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ObjectMapper objectMapper;

    public DomainEventPublisher(RabbitTemplate rabbitTemplate,
                                ApplicationEventPublisher applicationEventPublisher,
                                ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
        this.objectMapper = objectMapper;
    }

    public void publish(DomainEvent event) {
        // 发布到本地应用事件
        applicationEventPublisher.publishEvent(event);

        // 发布到消息队列
        //publishToMessageQueue(event);
    }

    private void publishToMessageQueue(DomainEvent event) {
        String routingKey = getRoutingKey(event);

        // 创建一个不包含类型信息的消息
        String messageBody;
        try {
            // 手动序列化，不包含类型信息
            messageBody = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            System.err.println("❌ 序列化事件失败: " + e.getMessage());
            return;
        }

        Message message = MessageBuilder
                .withBody(messageBody.getBytes(StandardCharsets.UTF_8))
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();

        rabbitTemplate.send(RabbitMQConfig.DOMAIN_EVENTS_EXCHANGE, routingKey, message);

        System.out.println("📤 [COMMAND] 发布到 RabbitMQ: " + event.getEventType() + " - " + event.getAggregateId());
    }

    private String getRoutingKey(DomainEvent event) {
        return "user." + event.getEventType().toLowerCase();
    }
}