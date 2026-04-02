package com.order_service.controller;

import com.order_service.dto.OrderWrapperDto;
import com.order_service.response.ApiResponse;
import com.order_service.service.KafkaService;
import com.order_service.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    private static final Logger logger= LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private KafkaService kafkaService;

    public OrderController(OrderService orderService, KafkaService kafkaService){

        this.orderService=orderService;
        this.kafkaService=kafkaService;
    }

    @GetMapping("/order/test")
    public ResponseEntity<String> testEndPoint(@RequestParam("msg") String msg){

        return ResponseEntity.status(200).body("Hello "+msg);
    }

    @PostMapping("/order/save")
    public ResponseEntity<ApiResponse<OrderWrapperDto>> postOrder(@RequestBody OrderWrapperDto orderWrapperDto){

        logger.info("Order request is received.......");

        orderService.processOrder(orderWrapperDto);
        return ResponseEntity.status(200)
                .body(new ApiResponse<>("Order Created Successfully",orderWrapperDto));

    }

    @GetMapping("/order/test-kafka")
    public ResponseEntity<String> testKafka(@RequestParam("orderId") Integer orderId,@RequestParam("reason") String reason){

        this.orderService.cancelOrder(orderId,reason,"PAYMENT_FAILED");
        return ResponseEntity.status(200).body("Success.......");
    }
}
