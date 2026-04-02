package com.inventory_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name="order_processed")
public class OrderProcessedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    Integer orderId;

    @Column(name = "product_id")
    Integer productId;

    @Column(name = "reason")
    String reason;

    @Column(name= "quantity")
    Integer quantity;

    public OrderProcessedEntity(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderProcessedEntity{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", productId=" + productId +
                ", reason='" + reason + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
