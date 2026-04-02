package com.kafka_events;

import java.math.BigDecimal;

public class PaymentSuccessEvent {

    private Integer orderId;

    private BigDecimal amount;

    public PaymentSuccessEvent(){}
    public PaymentSuccessEvent(Integer orderId,BigDecimal amount){
        this.orderId=orderId;
        this.amount=amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
