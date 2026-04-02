package com.payment_service.mapper;

import com.payment_service.dto.PaymentDto;
import com.payment_service.entity.Payment;

public class PaymentMapper {

    public static PaymentDto getPaymentDto(Payment payment){

        PaymentDto paymentDto=new PaymentDto();

        if(payment.getId()!=null) paymentDto.setId(payment.getId());
        if(payment.getOrderId()!=null) paymentDto.setOrderId(payment.getOrderId());
        if(payment.getAmount()!=null) paymentDto.setAmount(payment.getAmount());
        if(payment.getCurrency()!=null) paymentDto.setCurrency(payment.getCurrency());
        if(payment.getStatus()!=null) paymentDto.setStatus(payment.getStatus());

        return paymentDto;
    }

    public static Payment getPaymentEntity(PaymentDto paymentDto){

        Payment payment = new Payment();

        if(paymentDto.getId() != null) payment.setId(paymentDto.getId());
        if(paymentDto.getOrderId() != null) payment.setOrderId(paymentDto.getOrderId());
        if(paymentDto.getAmount() != null) payment.setAmount(paymentDto.getAmount());
        if(paymentDto.getCurrency() != null) payment.setCurrency(paymentDto.getCurrency());
        if(paymentDto.getStatus() != null) payment.setStatus(paymentDto.getStatus());

        return payment;
    }
}
