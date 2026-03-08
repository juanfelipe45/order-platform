package com.usabana.orderservice.adapter.out.messaging;

import com.usabana.orderservice.adapter.out.messaging.event.OrderCreatedEvent;
import com.usabana.orderservice.application.port.out.OrderEventPublisherPort;
import com.usabana.orderservice.config.RabbitMQConfig;
import com.usabana.orderservice.domain.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderEventPublisherAdapter implements OrderEventPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    public RabbitOrderEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishOrderCreated(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId(),
                order.getProductId(),
                order.getQuantity()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                "order.created",
                event
        );
    }
}