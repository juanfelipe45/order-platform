package com.usabana.inventoryservice.listener;

import com.usabana.inventoryservice.config.RabbitMQConfig;
import com.usabana.inventoryservice.event.OrderCreatedEvent;
import com.usabana.inventoryservice.publisher.InventoryEventPublisher;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final InventoryEventPublisher inventoryEventPublisher;

    public OrderCreatedListener(InventoryEventPublisher inventoryEventPublisher) {
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("Evento recibido en inventory-service para orderId=" + event.getOrderId());

        if (event.getQuantity() <= 5) {
            inventoryEventPublisher.publishReserved(event.getOrderId());
            System.out.println("Stock reservado para orderId=" + event.getOrderId());
        } else {
            inventoryEventPublisher.publishRejected(event.getOrderId());
            System.out.println("Stock insuficiente para orderId=" + event.getOrderId());
        }
    }
}