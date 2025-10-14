package com.eventbridge.query.infrastructure.events;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.eventbridge.common.event.EventProcessorRegistry;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.config.RabbitMQConfig;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RabbitMQEventsListener {

    private final EventProcessorRegistry eventProcessorRegistry;

    public RabbitMQEventsListener(EventProcessorRegistry eventProcessorRegistry) {
        this.eventProcessorRegistry = eventProcessorRegistry;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_EVENTS_QUEUE)
    public void handleUserEvent(DomainEvent event) {
        System.out.println("Received event from RabbitMQ: " + event.getEventType() + " for user: " + event.getAggregateId());

        try {
            eventProcessorRegistry.process(event.getEventType(), event);
            System.out.println("Successfully processed event: " + event.getEventType());
        } catch (Exception e) {
            System.err.println("Error processing event " + event.getEventType() + ": " + e.getMessage());
            // 在实际应用中，这里应该将失败的事件发送到死信队列或记录错误日志
        }
    }
}