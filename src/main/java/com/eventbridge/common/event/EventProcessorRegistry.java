package com.eventbridge.common.event;

import org.springframework.stereotype.Component;
import com.eventbridge.command.domain.events.DomainEvent;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Component
public class EventProcessorRegistry {

    private final Map<String, Consumer<DomainEvent>> processors = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // 可以注册不同类型事件的处理逻辑
    }

    public void registerProcessor(String eventType, Consumer<DomainEvent> processor) {
        processors.put(eventType, processor);
    }

    public void process(String eventType, DomainEvent event) {
        Consumer<DomainEvent> processor = processors.get(eventType);
        if (processor != null) {
            processor.accept(event);
        }
    }
}
