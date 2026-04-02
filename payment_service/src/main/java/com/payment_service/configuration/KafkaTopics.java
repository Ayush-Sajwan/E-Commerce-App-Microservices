package com.payment_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopics {

    @Bean
    public NewTopic paymentSuccessTopic(@Value("${kafka-event.payment-success-topic}") String paymentSuccessTopic){

        return TopicBuilder.name(paymentSuccessTopic).build();
    }

    @Bean
    public NewTopic paymentFailedTopic(@Value("${kafka-event.payment-failed-topic}") String paymentFailedTopic){

        return TopicBuilder.name(paymentFailedTopic).build();
    }
}
