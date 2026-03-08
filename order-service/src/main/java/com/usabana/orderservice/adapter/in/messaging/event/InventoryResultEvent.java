package com.usabana.orderservice.adapter.in.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResultEvent {

    private Long orderId;
    private String status;
    private String message;
}