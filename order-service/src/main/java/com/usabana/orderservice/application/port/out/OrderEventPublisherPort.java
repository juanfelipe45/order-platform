package com.usabana.orderservice.application.port.out;

import com.usabana.orderservice.domain.model.Order;

public interface OrderEventPublisherPort {

    void publishOrderCreated(Order order);

}