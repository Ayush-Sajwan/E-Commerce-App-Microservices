package com.payment_service.service;

import com.payment_service.dto.PaymentDto;
import com.payment_service.entity.Payment;
import com.payment_service.exception.PaymentProcessingException;
import com.payment_service.mapper.PaymentMapper;
import com.payment_service.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private static final Logger logger= LoggerFactory.getLogger(PaymentService.class);

    private PaymentRepository paymentRepository;
    private KafkaService kafkaService;

    public PaymentService(PaymentRepository paymentRepository,KafkaService kafkaService){
        this.paymentRepository=paymentRepository;
        this.kafkaService=kafkaService;
        logger.info("Payment Service class successfully initialized........");
    }

    public PaymentDto createPayment(PaymentDto paymentDto){

        logger.info("Got the request for creating payment.....");
        if(paymentDto.getOrderId()==null){
            logger.info("Cannot create payment with order id as null");
            throw new PaymentProcessingException("Order Id is null cannot process the payment creation order.......");
        }

        //checking if the payment with the order id already exists
        //if yes then returning that only
        Payment test=this.paymentRepository.getPaymentByOrderId(paymentDto.getOrderId());
        if(test!=null){
            logger.info("Payment with order id already exists......");
            return PaymentMapper.getPaymentDto(test);
        }

        //getting the payment entity
        Payment payment= PaymentMapper.getPaymentEntity(paymentDto);
        //setting the payment status
        payment.setStatus("CREATED");

        //saving the payment in database
        payment=this.paymentRepository.save(payment);

        paymentDto=PaymentMapper.getPaymentDto(payment);

        logger.info("Successfully processed the payment creation request.........");
        return paymentDto;
    }

    public PaymentDto completePayment(PaymentDto paymentDto){

        logger.info("Got the payment completing request.....");

        Payment payment=this.paymentRepository.getPaymentByOrderIdAndId(paymentDto.getOrderId(),paymentDto.getId());
        if(payment==null){
            logger.info("Payment with payment id:{} and orderId:{} does not exist",paymentDto.getId(),paymentDto.getOrderId());
            throw new PaymentProcessingException("Payment with payment id and orderId does not exist");
        }

        if(payment.getAmount().compareTo(paymentDto.getAmount())!=0){
            logger.info("Payment with payment id:{} and orderId:{}  amount does not match with data",paymentDto.getId(),paymentDto.getOrderId());
            throw new PaymentProcessingException("Payment amount invalid please give valid amount");
        }
        //if everything is right then updating the status
        payment.setStatus("PAYMENT-COMPLETED");
        payment=this.paymentRepository.save(payment);

        //now dispatching the payment success event
        this.kafkaService.generatePaymentSuccessEvent(payment.getOrderId(), payment.getAmount() );
        logger.info("Successfully processed the payment confirmation request...........");

        return PaymentMapper.getPaymentDto(payment);
    }
}
