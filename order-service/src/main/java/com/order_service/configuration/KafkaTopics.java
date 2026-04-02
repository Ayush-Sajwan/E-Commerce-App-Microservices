package com.order_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    @Bean
    public NewTopic orderTopic(@Value("${kafka-event.inventory-release-topic}")String inventoryReleaseTopic){

        //building the topic for inventory release
        return TopicBuilder.name(inventoryReleaseTopic).build();
    }

}
