package com.order_service.feignClients;

import com.order_service.dto.GeneralResponseDto;
import com.order_service.dto.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @PostMapping("/payment/create")
    GeneralResponseDto<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto);
}
