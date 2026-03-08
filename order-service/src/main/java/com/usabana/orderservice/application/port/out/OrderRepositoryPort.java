package com.usabana.orderservice.application.port.out;

import com.usabana.orderservice.domain.model.Order;

public interface OrderRepositoryPort {

    Order save(Order order);

    Order findById(Long id);

}