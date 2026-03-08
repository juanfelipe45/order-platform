package com.usabana.notificationservice.listener;

import com.usabana.notificationservice.config.RabbitMQConfig;
import com.usabana.notificationservice.event.InventoryResultEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryNotificationListener {

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_RESERVED_QUEUE)
    public void handleReserved(InventoryResultEvent event) {
        System.out.println("NOTIFICACIÓN: Pedido " + event.getOrderId() + " confirmado. " + event.getMessage());
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_REJECTED_QUEUE)
    public void handleRejected(InventoryResultEvent event) {
        System.out.println("NOTIFICACIÓN: Pedido " + event.getOrderId() + " rechazado. " + event.getMessage());
    }
}