package com.usabana.orderservice.application.port.in;

import com.usabana.orderservice.domain.model.Order;

public interface GetOrderUseCase {
    Order getOrderById(Long id);
}
