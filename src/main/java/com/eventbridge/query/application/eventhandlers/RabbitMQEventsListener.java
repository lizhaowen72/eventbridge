package com.eventbridge.query.infrastructure.events;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
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
                                  ObjectMapper rabbitMQObjectMapper) {
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
                System.err.println("❌ [QUERY-RABBITMQ] 无法转换消息为 DomainEvent: " +
                        (message != null ? message.getClass().getName() : "null"));
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 处理消息失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private DomainEvent convertToDomainEvent(Object message) {
        try {
            System.out.println("🔍 [QUERY-RABBITMQ] 消息类型: " +
                    (message != null ? message.getClass().getName() : "null"));

            // 处理 Spring AMQP Message 类型
            if (message instanceof Message) {
                Message amqpMessage = (Message) message;
                return convertFromAmqpMessage(amqpMessage);
            }
            // 处理 LinkedHashMap 类型（经过转换器处理后的消息）
            else if (message instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) message;
                return convertFromMap(map);
            }
            // 处理已经转换的 DomainEvent 类型
            else if (message instanceof DomainEvent) {
                return (DomainEvent) message;
            }
            // 处理字符串类型
            else if (message instanceof String) {
                String jsonString = (String) message;
                return convertFromJsonString(jsonString);
            }
            else {
                System.err.println("❌ [QUERY-RABBITMQ] 不支持的消息类型: " +
                        (message != null ? message.getClass().getName() : "null"));
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 转换消息失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 处理原始的 AMQP Message
     */
    private DomainEvent convertFromAmqpMessage(Message amqpMessage) {
        try {
            // 获取消息内容
            byte[] body = amqpMessage.getBody();
            String contentType = amqpMessage.getMessageProperties().getContentType();

            System.out.println("🔍 [QUERY-RABBITMQ] AMQP 消息详情:");
            System.out.println("   Content-Type: " + contentType);
            System.out.println("   Body length: " + body.length + " bytes");

            // 将字节数组转换为字符串
            String jsonString = new String(body, StandardCharsets.UTF_8);
            System.out.println("🔍 [QUERY-RABBITMQ] 消息内容: " + jsonString);

            return convertFromJsonString(jsonString);

        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 转换 AMQP 消息失败: " + e.getMessage());
            return null;
        }
    }

    /**
     * 处理 JSON 字符串
     */
    private DomainEvent convertFromJsonString(String jsonString) {
        try {
            // 先解析为通用 Map 来获取 eventType
            Map<String, Object> map = rabbitMQObjectMapper.readValue(jsonString, Map.class);
            return convertFromMap(map);
        } catch (Exception e) {
            System.err.println("❌ [QUERY-RABBITMQ] 转换 JSON 字符串失败: " + e.getMessage());
            return null;
        }
    }

    /**
     * 从 Map 转换为具体的 DomainEvent
     */
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

    /**
     * 诊断方法：打印消息详情
     */
    private void printMessageDetails(Object message) {
        if (message instanceof Message) {
            Message amqpMessage = (Message) message;
            System.out.println("📋 AMQP 消息详情:");
            System.out.println("   MessageProperties: " + amqpMessage.getMessageProperties());
            System.out.println("   Body: " + new String(amqpMessage.getBody(), StandardCharsets.UTF_8));
        } else {
            System.out.println("📋 消息内容: " + message);
        }
    }
}