package com.usabana.orderservice.adapter.out.persistence;

import com.usabana.orderservice.application.port.out.OrderRepositoryPort;
import com.usabana.orderservice.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryOrderRepositoryAdapter implements OrderRepositoryPort {

    private final Map<Long, Order> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(idGenerator.getAndIncrement());
        }

        database.put(order.getId(), order);
        return order;
    }

    @Override
    public Order findById(Long id) {
        return database.get(id);
    }
}