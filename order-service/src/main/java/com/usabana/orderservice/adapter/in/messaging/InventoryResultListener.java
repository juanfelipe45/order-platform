package com.usabana.orderservice.adapter.in.messaging;

import com.usabana.orderservice.adapter.in.messaging.event.InventoryResultEvent;
import com.usabana.orderservice.application.port.in.UpdateOrderStatusUseCase;
import com.usabana.orderservice.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryResultListener {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;

    public InventoryResultListener(UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_RESERVED_QUEUE)
    public void handleReserved(InventoryResultEvent event) {
        updateOrderStatusUseCase.updateOrderStatus(event.getOrderId(), event.getStatus());
        System.out.println("Pedido " + event.getOrderId() + " actualizado a CONFIRMED");
    }

    @RabbitListener(queues = RabbitMQConfig.INVENTORY_REJECTED_QUEUE)
    public void handleRejected(InventoryResultEvent event) {
        updateOrderStatusUseCase.updateOrderStatus(event.getOrderId(), event.getStatus());
        System.out.println("Pedido " + event.getOrderId() + " actualizado a REJECTED");
    }
}