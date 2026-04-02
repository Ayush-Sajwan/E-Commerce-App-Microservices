package com.payment_service.service;

import com.kafka_events.PaymentFailedEvent;
import com.kafka_events.PaymentSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class KafkaService {

    private static final Logger logger= LoggerFactory.getLogger(KafkaService.class);

    private String paymentSuccessTopic;
    private String paymentFailedTopic;

    private KafkaTemplate<String, PaymentSuccessEvent> paymentSuccessEventKafkaTemplate;
    private KafkaTemplate<String , PaymentFailedEvent> paymentFailedEventKafkaTemplate;

    public KafkaService(@Value("${kafka-event.payment-success-topic}") String paymentSuccessTopic,
                        @Value("${kafka-event.payment-failed-topic}") String paymentFailedTopic,
                        KafkaTemplate<String, PaymentSuccessEvent> paymentSuccessEventKafkaTemplate,
                        KafkaTemplate<String , PaymentFailedEvent> paymentFailedEventKafkaTemplate
                        ){

        logger.info("Payment Success Topic: {}",paymentSuccessTopic);
        this.paymentSuccessTopic=paymentSuccessTopic;
        logger.info("Payment Failure Topic: {}",paymentFailedTopic);
        this.paymentFailedTopic=paymentFailedTopic;

        this.paymentSuccessEventKafkaTemplate=paymentSuccessEventKafkaTemplate;
        this.paymentFailedEventKafkaTemplate=paymentFailedEventKafkaTemplate;

        logger.info("Kafka Service is successfully initialized.........");
    }

    public void generatePaymentSuccessEvent(Integer orderId, BigDecimal amount){

        PaymentSuccessEvent paymentSuccessEvent=new PaymentSuccessEvent(orderId,amount);
        Message<PaymentSuccessEvent> message= MessageBuilder
                .withPayload(paymentSuccessEvent)
                .setHeader(KafkaHeaders.TOPIC,this.paymentSuccessTopic)
                .build();

        this.paymentSuccessEventKafkaTemplate.send(message);
        logger.info("Successfully sent the payment success event for orderId:{}",orderId);

    }

    public void generatePaymentFailureEvent(Integer orderId,BigDecimal amount){

        PaymentFailedEvent paymentFailedEvent=new PaymentFailedEvent(orderId,amount);
        Message<PaymentFailedEvent> message=MessageBuilder
                .withPayload(paymentFailedEvent)
                .setHeader(KafkaHeaders.TOPIC,this.paymentFailedTopic)
                .build();

        this.paymentFailedEventKafkaTemplate.send(message);
        logger.info("Successfully sent the payment failed event for orderId:{}",orderId);
    }


}
