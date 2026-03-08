package com.usabana.orderservice.application.usecase;

import com.usabana.orderservice.application.port.in.CreateOrderUseCase;
import com.usabana.orderservice.application.port.in.GetOrderUseCase;
import com.usabana.orderservice.application.port.in.UpdateOrderStatusUseCase;
import com.usabana.orderservice.application.port.out.OrderEventPublisherPort;
import com.usabana.orderservice.application.port.out.OrderRepositoryPort;
import com.usabana.orderservice.domain.model.Order;
import com.usabana.orderservice.domain.model.OrderStatus;
import org.springframework.stereotype.Service;

@Service
public class OrderUseCaseService implements CreateOrderUseCase, GetOrderUseCase, UpdateOrderStatusUseCase {

    private final OrderRepositoryPort orderRepositoryPort;
    private final OrderEventPublisherPort orderEventPublisherPort;

    public OrderUseCaseService(OrderRepositoryPort orderRepositoryPort, OrderEventPublisherPort orderEventPublisherPort) {
        this.orderRepositoryPort = orderRepositoryPort;
        this.orderEventPublisherPort = orderEventPublisherPort;
    }

    @Override
    public Order createOrder(String productId, Integer quantity) {
        Order order = new Order(null, productId, quantity, OrderStatus.PENDING);
        Order savedOrder = orderRepositoryPort.save(order);
        orderEventPublisherPort.publishOrderCreated(savedOrder);
        return savedOrder;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepositoryPort.findById(id);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepositoryPort.findById(orderId);

        if (order == null) {
            return;
        }

        if ("CONFIRMED".equalsIgnoreCase(status)) {
            order.confirm();
        } else if ("REJECTED".equalsIgnoreCase(status)) {
            order.reject();
        }

        orderRepositoryPort.save(order);
    }
}
