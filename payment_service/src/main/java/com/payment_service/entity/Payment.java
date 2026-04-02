package com.payment_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="payments")
public class Payment {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "id")
    private Integer id;

    @Column(name="order_id")
    private Integer orderId;

    @Column(name="status")
    private String status;

    @Column(name="amount")
    private BigDecimal amount;

    @Column(name="currency")
    private String currency;

    public Payment(){}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
