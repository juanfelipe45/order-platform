package com.usabana.orderservice.adapter.in.web.dto;

public class CreateOrderRequest {

    private String productId;
    private Integer quantity;

    public CreateOrderRequest() {
    }

    public String getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}