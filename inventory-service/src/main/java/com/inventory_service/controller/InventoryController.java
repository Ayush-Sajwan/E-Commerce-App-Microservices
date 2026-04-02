package com.inventory_service.controller;

import com.inventory_service.dtos.InventoryGroups;
import com.inventory_service.dtos.Inventory_Dto;
import com.inventory_service.exception.ControllerRequestException;
import com.inventory_service.response.ApiResponse;
import com.inventory_service.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

    private static final Logger logger= LoggerFactory.getLogger(InventoryController.class);

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService){
        this.inventoryService=inventoryService;
        logger.info("Inventory Controller Registered.............");
    }

    @PostMapping("/inventory/create")
    public ResponseEntity<ApiResponse<Inventory_Dto>> createInventory(@Validated(InventoryGroups.createInventory.class)
                                                                      @RequestBody Inventory_Dto inventoryDto){
        logger.info("Got the request for the creating inventory.........");

        inventoryDto=this.inventoryService.createInventory(inventoryDto);

        logger.info("Returning the success response.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully created the inventory.......",inventoryDto));

    }

    @GetMapping("/inventory/get")
    public ResponseEntity<ApiResponse<Inventory_Dto>> getInventoryById(@RequestParam("id") Integer id){

        logger.info("Got the request for the getting inventory {id:"+id+"}");

        Inventory_Dto inventoryDto=this.inventoryService.getInventoryById(id);

        logger.info("Returning the success response.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully got the inventory.......",inventoryDto));

    }

    @PatchMapping("/inventory/update")
    public ResponseEntity<ApiResponse<Inventory_Dto>> updateInventory(@Validated(InventoryGroups.updateInventory.class)
            @RequestBody Inventory_Dto inventoryDto){

        logger.info("Got the request for the updating inventory.........");

        inventoryDto=this.inventoryService.updateInventory(inventoryDto,inventoryDto.getProduct_id());

        logger.info("Returning the success response.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully updated the inventory.......",inventoryDto));

    }

    @GetMapping("/inventory/reserve-stock")
    public ResponseEntity<ApiResponse<String>> reserveStock(@RequestParam("productId") Integer productId,
            @RequestParam("quantity") Integer quantity){

        if (productId==null){
            logger.info("ProductId is null ");
            throw new ControllerRequestException("ProductId is null");
        }

        if (quantity==null || quantity<=0){
            logger.info("Quantity is null or either less than or equal to zero {quantity: "+quantity+"}");
            throw new ControllerRequestException("Valid Quantity is not specified....");
        }

        //reserving the stock
        this.inventoryService.reserveStock(productId,quantity);

        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Success","Successfully reserved the stock"));

    }
}
