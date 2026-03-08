package com.usabana.orderservice.application.port.in;

import com.usabana.orderservice.domain.model.Order;

public interface CreateOrderUseCase {
    Order createOrder(String productId, Integer quantity);
}
