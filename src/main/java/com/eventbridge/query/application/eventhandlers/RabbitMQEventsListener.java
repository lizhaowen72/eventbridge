package com.eventbridge.query.application.eventhandlers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.eventbridge.common.event.EventProcessorRegistry;
import com.eventbridge.command.domain.events.DomainEvent;
import com.eventbridge.command.domain.events.UserCreatedEvent;
import com.eventbridge.command.domain.events.UserEmailUpdatedEvent;
import com.eventbridge.command.domain.events.UserDeactivatedEvent;
import com.eventbridge.config.RabbitMQConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Transactional
public class RabbitMQEventsListener {

    private final EventProcessorRegistry eventProcessorRegistry;
    private final ObjectMapper rabbitMQObjectMapper;

    public RabbitMQEventsListener(EventProcessorRegistry eventProcessorRegistry,
                                  @Qualifier("rabbitMQObjectMapper")ObjectMapper rabbitMQObjectMapper) {
        this.eventProcessorRegistry = eventProcessorRegistry;
        this.rabbitMQObjectMapper = rabbitMQObjectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_EVENTS_QUEUE)
    public void handleUserEvent(Object message,
                                @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {
        System.out.println("📥 [QUERY-RABBITMQ] 接收到 RabbitMQ 消息，路由键: " + routingKey);

        try {
            DomainEvent event = convertToDomainEvent(message);
            if (event != null) {
                System.out.println("🔄 [QUERY-RABBITMQ] 成功转换事件: " + event.getEventType() + " - " + event.getAggregateId());
                eventProcessorRegistry.process(event.getEventType(), event);
                System.out.println("✅ [QUERY-RABBITMQ] 事件处理成功: " + event.getEventType());
            } else {
                System.err.println("❌ [QUERY-RABBITMQ] 无法转换消息为 DomainEvent: " + message.getClass().getName());
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 处理消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private DomainEvent convertToDomainEvent(Object message) {
        try {
            System.out.println("🔍 [QUERY-RABBITMQ] 消息类型: " + message.getClass().getName());

            if (message instanceof DomainEvent) {
                // 如果消息已经是 DomainEvent 类型，直接返回
                return (DomainEvent) message;
            } else if (message instanceof LinkedHashMap) {
                // 处理 LinkedHashMap 情况
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) message;
                return convertFromMap(map);
            } else if (message instanceof String) {
                // 处理字符串情况（原始 JSON）
                String jsonString = (String) message;
                Map<String, Object> map = rabbitMQObjectMapper.readValue(jsonString, Map.class);
                return convertFromMap(map);
            } else {
                System.err.println("❌ [QUERY-RABBITMQ] 不支持的消息类型: " + message.getClass().getName());
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 转换消息失败: " + e.getMessage());
            return null;
        }
    }

    private DomainEvent convertFromMap(Map<String, Object> map) {
        try {
            // 从 map 中获取 eventType
            String eventType = (String) map.get("eventType");
            System.out.println("🔍 [QUERY-RABBITMQ] 事件类型: " + eventType);

            if (eventType == null) {
                System.err.println("❌ [QUERY-RABBITMQ] 消息中没有 eventType 字段");
                System.err.println("   可用字段: " + map.keySet());
                return null;
            }

            // 根据 eventType 转换为具体的事件类型
            switch (eventType) {
                case "UserCreated":
                    return rabbitMQObjectMapper.convertValue(map, UserCreatedEvent.class);
                case "UserEmailUpdated":
                    return rabbitMQObjectMapper.convertValue(map, UserEmailUpdatedEvent.class);
                case "UserDeactivated":
                    return rabbitMQObjectMapper.convertValue(map, UserDeactivatedEvent.class);
                default:
                    System.err.println("❌ [QUERY-RABBITMQ] 未知的事件类型: " + eventType);
                    return null;
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 从 Map 转换事件失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}