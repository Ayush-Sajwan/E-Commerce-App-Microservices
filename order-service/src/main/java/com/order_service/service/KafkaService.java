package com.order_service.service;

import com.kafka_events.InventoryDeductEvent;
import com.kafka_events.InventoryReleaseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private static final Logger logger= LoggerFactory.getLogger(KafkaService.class);


    private String inventoryReleaseTopic;
    private String inventoryDeductTopic;

    private KafkaTemplate<String,InventoryReleaseEvent> inventoryReleaseEventKafkaTemplate;
    private KafkaTemplate<String, InventoryDeductEvent> inventoryDeductEventKafkaTemplate;

    public KafkaService(@Value("${kafka-event.inventory-release-topic}")String inventoryReleaseTopic,
                        @Value("${kafka-event.inventory-deduct-topic}")String inventoryDeductTopic,
                        KafkaTemplate<String,InventoryReleaseEvent> inventoryReleaseEventKafkaTemplate,
                        KafkaTemplate<String,InventoryDeductEvent> inventoryDeductEventKafkaTemplate){

        logger.info("Inventory Release Topic {}",inventoryReleaseTopic);
        this.inventoryReleaseTopic=inventoryReleaseTopic;

        logger.info("Inventory Deduct Topic {}",inventoryDeductTopic);
        this.inventoryDeductTopic=inventoryDeductTopic;

        this.inventoryReleaseEventKafkaTemplate=inventoryReleaseEventKafkaTemplate;

        this.inventoryDeductEventKafkaTemplate=inventoryDeductEventKafkaTemplate;

        logger.info("Kafka service is initialized....");
    }


    public void inventoryRelease(Integer orderId,Integer productId,Integer quantity,String reason){

        InventoryReleaseEvent inventoryReleaseEvent=new InventoryReleaseEvent();
        inventoryReleaseEvent.setOrderId(orderId);
        inventoryReleaseEvent.setProductId(productId);
        inventoryReleaseEvent.setQuantity(quantity);
        inventoryReleaseEvent.setReason(reason);

       Message<InventoryReleaseEvent> message=MessageBuilder
               .withPayload(inventoryReleaseEvent)
               .setHeader(KafkaHeaders.TOPIC,this.inventoryReleaseTopic)
               .build();

       this.inventoryReleaseEventKafkaTemplate.send(message);
        logger.info("Inventory Release event for {orderId:{}, productId:{}, quantity:{}, reason:{}} has been published", orderId, productId, quantity, reason);


    }

    public void inventoryDeduct(Integer orderId,Integer productId,Integer quantity,String reason){

        InventoryDeductEvent inventoryDeductEvent=new InventoryDeductEvent();
        inventoryDeductEvent.setOrderId(orderId);
        inventoryDeductEvent.setProductId(productId);
        inventoryDeductEvent.setQuantity(quantity);
        inventoryDeductEvent.setReason(reason);

        Message<InventoryDeductEvent> message=MessageBuilder
                .withPayload(inventoryDeductEvent)
                .setHeader(KafkaHeaders.TOPIC,this.inventoryDeductTopic)
                .build();

        this.inventoryDeductEventKafkaTemplate.send(message);
        logger.info("Inventory Deduct event for {orderId:{}, productId:{}, quantity:{}, reason:{}} has been published", orderId, productId, quantity, reason);


    }
}
