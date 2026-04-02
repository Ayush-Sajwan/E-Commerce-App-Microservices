package com.order_service.mapper;

import com.order_service.dto.OrderDto;
import com.order_service.entity.Order;

public class OrderMapper {

    public static Order getOrderEntity(OrderDto orderDto){

        Order order=new Order();

        if(orderDto.getId()!=null) order.setId(orderDto.getId());
        if(orderDto.getUserId()!=null) order.setUserId(orderDto.getUserId());
        if(orderDto.getStatus()!=null) order.setStatus(orderDto.getStatus());
        if(orderDto.getTotalAmount()!=null) order.setTotalAmount(orderDto.getTotalAmount());


        return order;
    }

    public static OrderDto getOrderDto(Order order) {

        OrderDto orderDto = new OrderDto();

        if (order.getId() != null) orderDto.setId(order.getId());
        if (order.getUserId() != null) orderDto.setUserId(order.getUserId());
        if (order.getStatus() != null) orderDto.setStatus(order.getStatus());
        if (order.getTotalAmount() != null) orderDto.setTotalAmount(order.getTotalAmount());

        return orderDto;
    }
}
