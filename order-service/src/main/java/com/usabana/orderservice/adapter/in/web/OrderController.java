package com.usabana.orderservice.adapter.in.web;

import com.usabana.orderservice.adapter.in.web.dto.CreateOrderRequest;
import com.usabana.orderservice.application.port.in.CreateOrderUseCase;
import com.usabana.orderservice.application.port.in.GetOrderUseCase;
import com.usabana.orderservice.domain.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase,
                           GetOrderUseCase getOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {

        Order createdOrder = createOrderUseCase.createOrder(
                request.getProductId(),
                request.getQuantity()
        );

        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {

        Order order = getOrderUseCase.getOrderById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }
}