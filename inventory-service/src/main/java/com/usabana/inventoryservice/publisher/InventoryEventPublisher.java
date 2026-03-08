package com.usabana.inventoryservice.publisher;

import com.usabana.inventoryservice.config.RabbitMQConfig;
import com.usabana.inventoryservice.event.InventoryResultEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public InventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishReserved(Long orderId) {
        InventoryResultEvent event = new InventoryResultEvent(
                orderId,
                "CONFIRMED",
                "Stock reservado"
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                "inventory.reserved",
                event
        );
    }

    public void publishRejected(Long orderId) {
        InventoryResultEvent event = new InventoryResultEvent(
                orderId,
                "REJECTED",
                "Stock insuficiente"
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                "inventory.rejected",
                event
        );
    }
}