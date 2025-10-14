package com.eventbridge.command.infrastructure.events;

import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DomainEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher applicationEventPublisher;

    public DomainEventPublisher(RabbitTemplate rabbitTemplate,
                                ApplicationEventPublisher applicationEventPublisher) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publish(DomainEvent event) {
        // 发布到本地应用事件（用于事务同步）
        applicationEventPublisher.publishEvent(event);

        // 发布到消息队列（用于跨服务通信）
        publishToMessageQueue(event);
    }

    private void publishToMessageQueue(DomainEvent event) {
        String routingKey = getRoutingKey(event);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.DOMAIN_EVENTS_EXCHANGE,
                routingKey,
                event
        );

        System.out.println("Published event: " + event.getEventType() + " for aggregate: " + event.getAggregateId());
    }

    private String getRoutingKey(DomainEvent event) {
        if (event instanceof UserCreatedEvent ||
                event instanceof UserEmailUpdatedEvent ||
                event instanceof UserDeactivatedEvent) {
            return "user." + event.getEventType().toLowerCase();
        }
        return event.getEventType().toLowerCase();
    }
}