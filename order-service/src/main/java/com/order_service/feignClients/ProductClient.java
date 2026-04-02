package com.order_service.feignClients;

import com.order_service.dto.ProductDto;
import com.order_service.dto.GeneralResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/product/{id}")
    GeneralResponseDto<ProductDto> getProductById(@PathVariable("id") Integer id);
}
