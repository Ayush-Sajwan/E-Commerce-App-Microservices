package com.order_service.mapper;

import com.order_service.dto.OrderItemsDto;
import com.order_service.entity.OrderItems;

public class OrderItemsMapper {

    public static OrderItems getOrderItemsEntity(OrderItemsDto dto) {

        OrderItems entity = new OrderItems();

        if (dto.getId() != null) entity.setId(dto.getId());
        if (dto.getOrderId() != null) entity.setOrderId(dto.getOrderId());
        if (dto.getProductId() != null) entity.setProductId(dto.getProductId());
        if (dto.getProductName() != null) entity.setProductName(dto.getProductName());
        if (dto.getPrice() != null) entity.setPrice(dto.getPrice());
        if (dto.getQuantity() != null) entity.setQuantity(dto.getQuantity());
        if (dto.getTotal_price() != null) entity.setTotal_price(dto.getTotal_price());

        return entity;
    }


    public static OrderItemsDto getOrderItemsDto(OrderItems entity) {

        OrderItemsDto dto = new OrderItemsDto();

        if (entity.getId() != null) dto.setId(entity.getId());
        if (entity.getOrderId() != null) dto.setOrderId(entity.getOrderId());
        if (entity.getProductId() != null) dto.setProductId(entity.getProductId());
        if (entity.getProductName() != null) dto.setProductName(entity.getProductName());
        if (entity.getPrice() != null) dto.setPrice(entity.getPrice());
        if (entity.getQuantity() != null) dto.setQuantity(entity.getQuantity());
        if (entity.getTotal_price() != null) dto.setTotal_price(entity.getTotal_price());

        return dto;
    }
}
