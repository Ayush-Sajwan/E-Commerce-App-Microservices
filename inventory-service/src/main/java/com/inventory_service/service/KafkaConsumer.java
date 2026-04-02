package com.inventory_service.service;

import com.inventory_service.entity.OrderProcessedEntity;
import com.kafka_events.InventoryDeductEvent;
import com.kafka_events.InventoryReleaseEvent;
import com.inventory_service.repository.InventoryRepository;
import com.inventory_service.repository.OrderProcessedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.Acknowledgment;

@Service
public class KafkaConsumer {

    private static final Logger logger= LoggerFactory.getLogger(KafkaConsumer.class);
    private InventoryRepository inventoryRepository;
    private OrderProcessedRepository orderProcessedRepository;

    public KafkaConsumer(InventoryRepository inventoryRepository,OrderProcessedRepository orderProcessedRepository){

        this.inventoryRepository=inventoryRepository;
        this.orderProcessedRepository=orderProcessedRepository;

        logger.info("Kafka Consumer successfully initialized....");
    }


    @KafkaListener(topics = "${kafka-event.inventory-release-topic}",
    groupId = "inventory-group")
    public void processInventoryReleaseEvent(InventoryReleaseEvent inventoryReleaseEvent){

        logger.info("Got the inventory release event: {}",inventoryReleaseEvent);

        OrderProcessedEntity orderProcessedEntity=this.orderProcessedRepository
                .getProcessedOrderByOrderIdAndProductId(inventoryReleaseEvent.getOrderId(),inventoryReleaseEvent.getProductId());

        if(orderProcessedEntity!=null){
            logger.info("Inventory release event already processed.......");
            return;
        }

        logger.info("Processing the inventory release event....");

        orderProcessedEntity=new OrderProcessedEntity();
        orderProcessedEntity.setOrderId(inventoryReleaseEvent.getOrderId());
        orderProcessedEntity.setProductId(inventoryReleaseEvent.getProductId());
        orderProcessedEntity.setQuantity(inventoryReleaseEvent.getQuantity());
        orderProcessedEntity.setReason(inventoryReleaseEvent.getReason());

        int updated=this.inventoryRepository.releaseStockInInventory(inventoryReleaseEvent.getProductId(),inventoryReleaseEvent.getQuantity());
        logger.info("Updated rows:{}",updated);

        orderProcessedEntity=this.orderProcessedRepository.save(orderProcessedEntity);
        logger.info("Order processed: {}",orderProcessedEntity);
        logger.info("Successfully processed the event and released the stock.....");

    }

    @KafkaListener(topics = "${kafka-event.inventory-deduct-topic}",
            groupId = "inventory-deduct-group")
    public void processInventoryDeductEvent(InventoryDeductEvent inventoryDeductEvent){

        logger.info("Got the inventory deduct event: {}",inventoryDeductEvent);

        OrderProcessedEntity orderProcessedEntity=this.orderProcessedRepository
                .getProcessedOrderByOrderIdAndProductId(inventoryDeductEvent.getOrderId(),inventoryDeductEvent.getProductId());

        if(orderProcessedEntity!=null){
            logger.info("Inventory deduct event already processed.......");
            return;
        }

        logger.info("Processing the inventory deduct event....");

        orderProcessedEntity=new OrderProcessedEntity();
        orderProcessedEntity.setOrderId(inventoryDeductEvent.getOrderId());
        orderProcessedEntity.setProductId(inventoryDeductEvent.getProductId());
        orderProcessedEntity.setQuantity(inventoryDeductEvent.getQuantity());
        orderProcessedEntity.setReason(inventoryDeductEvent.getReason());

        int updated=this.inventoryRepository.deductStockInInventory(inventoryDeductEvent.getProductId(),inventoryDeductEvent.getQuantity());
        logger.info("Updated rows:{}",updated);

        orderProcessedEntity=this.orderProcessedRepository.save(orderProcessedEntity);
        logger.info("Order processed: {}",orderProcessedEntity);
        logger.info("Successfully processed the event and deducted the stock.....");

    }

}
