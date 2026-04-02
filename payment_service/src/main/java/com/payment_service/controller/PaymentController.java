package com.payment_service.controller;

import com.payment_service.dto.PaymentDto;
import com.payment_service.response.ApiResponse;
import com.payment_service.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    private static final Logger logger= LoggerFactory.getLogger(PaymentController.class);
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService=paymentService;

        logger.info("Payment Controller Successfully initialized...........");
    }

    @GetMapping("/payment/test")
    public ResponseEntity<String> testPaymentService(@RequestParam("msg") String msg){

        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body("Hello "+msg+" from payment service");
    }

    @PostMapping("/payment/create")
    public ResponseEntity<ApiResponse<PaymentDto>> createPayment(@RequestBody PaymentDto paymentDto){

        logger.info("Got request in controller to create the payment..........");
        logger.info("Calling the payment service to process the request........");

        paymentDto=this.paymentService.createPayment(paymentDto);

        logger.info("returning success response from the controller");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(
                        new ApiResponse<>("SUCCESS",paymentDto)
                );
    }

    @PostMapping("/payment/pay")
    public ResponseEntity<ApiResponse<PaymentDto>> doPayment(@RequestBody PaymentDto paymentDto){

        logger.info("Got request in controller to complete the payment..........");
        logger.info("Calling the payment service to complete the request........");

        paymentDto=this.paymentService.completePayment(paymentDto);

        logger.info("returning success response from the controller");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(
                        new ApiResponse<>("SUCCESS",paymentDto)
                );
    }


}
