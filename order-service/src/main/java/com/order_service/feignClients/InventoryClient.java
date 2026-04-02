package com.order_service.feignClients;

import com.order_service.dto.GeneralResponseDto;
import com.order_service.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="INVENTORY-SERVICE")
public interface InventoryClient {

    @GetMapping("/inventory/reserve-stock")
    GeneralResponseDto<String> reserveStockInventory(@RequestParam("productId") Integer productId,
                                                     @RequestParam("quantity") Integer quantity);

}
