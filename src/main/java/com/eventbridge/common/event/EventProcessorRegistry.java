package com.eventbridge.common.event;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import com.eventbridge.command.domain.events.DomainEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class EventProcessorRegistry {

    private final Map<String, Consumer<DomainEvent>> processors = new ConcurrentHashMap<>();

    /**
     * 应用启动完成后初始化
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        System.out.println("🎯 EventProcessorRegistry 初始化完成");
        System.out.println("   当前注册的处理器数量: " + processors.size());
    }

    /**
     * 注册事件处理器
     */
    public void registerProcessor(String eventType, Consumer<DomainEvent> processor) {
        if (eventType == null || eventType.trim().isEmpty()) {
            throw new IllegalArgumentException("事件类型不能为空");
        }
        if (processor == null) {
            throw new IllegalArgumentException("事件处理器不能为空");
        }

        processors.put(eventType, processor);
        System.out.println("📝 EventProcessorRegistry - 注册处理器: " + eventType);
    }

    /**
     * 处理事件
     */
    public void process(String eventType, DomainEvent event) {
        if (eventType == null || eventType.trim().isEmpty()) {
            System.err.println("❌ EventProcessorRegistry - 事件类型为空，无法处理");
            return;
        }
        if (event == null) {
            System.err.println("❌ EventProcessorRegistry - 事件对象为空，无法处理");
            return;
        }

        Consumer<DomainEvent> processor = processors.get(eventType);
        if (processor != null) {
            try {
                System.out.println("🚀 EventProcessorRegistry - 执行处理器: " + eventType + " for " + event.getAggregateId());
                processor.accept(event);
                System.out.println("✅ EventProcessorRegistry - 处理器执行成功: " + eventType);
            } catch (Exception e) {
                System.err.println("❌ EventProcessorRegistry - 处理器执行失败 " + eventType + ": " + e.getMessage());
                e.printStackTrace();
                // 在实际生产环境中，这里应该将失败的事件发送到死信队列
                handleProcessingFailure(eventType, event, e);
            }
        } else {
            System.out.println("⚠️ EventProcessorRegistry - 未找到事件处理器: " + eventType);
            // 可以选择记录未处理的事件，或者抛出异常
            handleUnprocessedEvent(eventType, event);
        }
    }

    /**
     * 检查是否存在指定事件类型的处理器
     */
    public boolean hasProcessor(String eventType) {
        return processors.containsKey(eventType);
    }

    /**
     * 获取注册的处理器数量
     */
    public int getProcessorCount() {
        return processors.size();
    }

    /**
     * 获取所有已注册的事件类型
     */
    public Set<String> getRegisteredEventTypes() {
        return processors.keySet();
    }

    /**
     * 注销事件处理器
     */
    public void unregisterProcessor(String eventType) {
        if (processors.remove(eventType) != null) {
            System.out.println("🗑️ EventProcessorRegistry - 注销处理器: " + eventType);
        } else {
            System.out.println("ℹ️ EventProcessorRegistry - 处理器不存在，无需注销: " + eventType);
        }
    }

    /**
     * 打印所有已注册的事件处理器
     */
    public void printRegisteredProcessors() {
        System.out.println("=== 📊 EventProcessorRegistry 已注册处理器 ===");
        if (processors.isEmpty()) {
            System.out.println("   暂无注册的处理器");
        } else {
            processors.keySet().forEach(eventType ->
                    System.out.println("   - " + eventType)
            );
        }
        System.out.println("   总计: " + processors.size() + " 个处理器");
        System.out.println("===========================================");
    }

    /**
     * 清空所有事件处理器（主要用于测试）
     */
    public void clearAllProcessors() {
        int count = processors.size();
        processors.clear();
        System.out.println("🧹 EventProcessorRegistry - 已清空所有 " + count + " 个处理器");
    }

    /**
     * 处理处理失败的情况
     */
    private void handleProcessingFailure(String eventType, DomainEvent event, Exception exception) {
        // 在实际生产环境中，这里应该：
        // 1. 记录失败事件到数据库
        // 2. 发送到死信队列
        // 3. 触发告警

        System.err.println("💥 事件处理失败 - 事件类型: " + eventType +
                ", 聚合ID: " + event.getAggregateId() +
                ", 错误: " + exception.getMessage());

        // 示例：简单的重试机制（生产环境应该更复杂）
        retryProcessing(eventType, event, exception);
    }

    /**
     * 处理未找到处理器的情况
     */
    private void handleUnprocessedEvent(String eventType, DomainEvent event) {
        // 在实际生产环境中，这里应该：
        // 1. 记录未处理事件
        // 2. 触发告警
        // 3. 可能尝试寻找备用处理器

        System.out.println("📋 未处理事件 - 事件类型: " + eventType +
                ", 聚合ID: " + event.getAggregateId() +
                ", 事件ID: " + event.getEventId());

        // 可以在这里添加逻辑来尝试处理未知事件类型
        handleUnknownEventType(eventType, event);
    }

    /**
     * 重试处理机制
     */
    private void retryProcessing(String eventType, DomainEvent event, Exception exception) {
        // 简单的重试逻辑
        int maxRetries = 3;
        int retryCount = 0;

        while (retryCount < maxRetries) {
            retryCount++;
            System.out.println("🔄 重试处理事件 (" + retryCount + "/" + maxRetries + "): " + eventType);

            try {
                Thread.sleep(1000 * retryCount); // 指数退避
                Consumer<DomainEvent> processor = processors.get(eventType);
                if (processor != null) {
                    processor.accept(event);
                    System.out.println("✅ 重试成功: " + eventType);
                    return;
                }
            } catch (Exception e) {
                System.err.println("❌ 重试失败 (" + retryCount + "): " + e.getMessage());
            }
        }

        System.err.println("💀 事件处理彻底失败，已放弃: " + eventType);
    }

    /**
     * 处理未知事件类型
     */
    private void handleUnknownEventType(String eventType, DomainEvent event) {
        // 可以在这里添加逻辑来处理未知事件类型
        // 例如：记录到日志、发送通知、尝试通用处理等

        System.out.println("🔍 发现未知事件类型: " + eventType);

        // 示例：尝试使用通配符处理器
        if (hasProcessor("*")) {
            System.out.println("🎯 使用通配符处理器处理未知事件: " + eventType);
            process("*", event);
        }
    }

    /**
     * 注册通配符处理器（处理所有未知事件类型）
     */
    public void registerWildcardProcessor(Consumer<DomainEvent> processor) {
        registerProcessor("*", processor);
    }

    /**
     * 批量注册事件处理器
     */
    public void registerProcessors(Map<String, Consumer<DomainEvent>> processorMap) {
        if (processorMap != null && !processorMap.isEmpty()) {
            processorMap.forEach(this::registerProcessor);
            System.out.println("📦 批量注册了 " + processorMap.size() + " 个事件处理器");
        }
    }

    /**
     * 获取注册统计信息
     */
    public String getStatistics() {
        return String.format(
                "EventProcessorRegistry 统计: 总处理器数=%d, 已注册事件类型=%s",
                processors.size(),
                getRegisteredEventTypes().stream().collect(Collectors.joining(", "))
        );
    }
}