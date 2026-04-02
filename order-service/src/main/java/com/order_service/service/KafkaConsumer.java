package com.order_service.service;

import com.kafka_events.PaymentFailedEvent;
import com.kafka_events.PaymentSuccessEvent;
import com.order_service.entity.Order;
import com.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger logger= LoggerFactory.getLogger(KafkaConsumer.class);

    private OrderRepository orderRepository;
    private OrderService orderService;

    public KafkaConsumer(OrderRepository orderRepository, OrderService orderService){
        this.orderRepository=orderRepository;
        this.orderService=orderService;
        logger.info("Kafka Consumer class successfully initialized......");
    }

    //listener for payment success
    @KafkaListener(topics = "${kafka-event.payment-success-topic}",
    groupId = "order-group")
    public void processPaymentSuccessEvent(PaymentSuccessEvent paymentSuccessEvent){

        logger.info("Received the payment success event.......");

        this.orderService.confirmOrder(paymentSuccessEvent.getOrderId(),"PAYMENT-SUCCESS","PAYMENT-SUCCESS");

        logger.info("Successfully processed the payment success event for order id: {}",paymentSuccessEvent.getOrderId());
    }

    //listener for payment failure
    @KafkaListener(topics = "${kafka-event.payment-failed-topic}",
            groupId = "order-group")
    public void processPaymentFailedEvent(PaymentFailedEvent paymentFailedEvent){

        logger.info("Received the payment failure event.......");

        //cancelling the order in database
        this.orderService.cancelOrder(paymentFailedEvent.getOrderId(),"Payment-Failed","PAYMENT-FAILED");

        logger.info("Successfully processed the payment failed event for order id: {}",paymentFailedEvent.getOrderId());

    }
}
