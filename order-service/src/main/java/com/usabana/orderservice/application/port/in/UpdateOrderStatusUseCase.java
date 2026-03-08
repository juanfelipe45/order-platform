package com.usabana.orderservice.application.port.in;

public interface UpdateOrderStatusUseCase {
    void updateOrderStatus(Long orderId, String status);
}
