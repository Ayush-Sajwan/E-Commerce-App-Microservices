package com.order_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderWrapperDto {
    @JsonProperty("order")
    public OrderDto orderDto;

    @JsonProperty("order-details")
    public List<OrderItemsDto> orderItemsDto;

    public PaymentDto paymentDto;

    public OrderDto getOrderDto() {
        return orderDto;
    }

    public void setOrderDto(OrderDto orderDto) {
        this.orderDto = orderDto;
    }

    public List<OrderItemsDto> getOrderItemsDto() {
        return orderItemsDto;
    }

    public void setOrderItemsDto(List<OrderItemsDto> orderItemsDto) {
        this.orderItemsDto = orderItemsDto;
    }

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }
}
