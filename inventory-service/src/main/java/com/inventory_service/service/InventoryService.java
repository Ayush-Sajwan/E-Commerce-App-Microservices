package com.inventory_service.service;

import com.inventory_service.dtos.Inventory_Dto;
import com.inventory_service.entity.Inventory;
import com.inventory_service.exception.InventoryProcessingException;
import com.inventory_service.mapper.InventoryMapper;
import com.inventory_service.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private static final Logger logger= LoggerFactory.getLogger(InventoryService.class);

    private InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository=inventoryRepository;
        logger.info("Inventory Service is created successfully.......");
    }

    public Inventory_Dto createInventory(Inventory_Dto inventoryDto){

        Inventory inventory=this.inventoryRepository.getInventoryByProductId(inventoryDto.getProduct_id());

        if(inventory!=null){
            logger.info("Inventory with product_id="+inventoryDto.getProduct_id() +"already exists cannot create new....");
            throw new InventoryProcessingException("Inventory with product_id="+inventoryDto.getProduct_id() +"already exits cannot create new....");
        }

        inventory= InventoryMapper.getInventory(inventoryDto);
        logger.info("Product_id-->"+inventory.getProduct_id());
        logger.info("Product_id DTO-->"+inventoryDto.getProduct_id());
        inventory=this.inventoryRepository.save(inventory);

        logger.info("Successfully created the new inventory......");

        return InventoryMapper.getInventoryDto(inventory);
    }

    public Inventory_Dto updateInventory(Inventory_Dto inventoryDto,Integer id){

        //getting the inventory item by the product id
        Inventory inventory=this.inventoryRepository.getInventoryByProductId(id);

        if(inventory==null){
            logger.info("Inventory with {product_id="+id+"} does not exist cannot update....");
            throw new InventoryProcessingException("Inventory with {product_id="+id+"} does not exist cannot update....");
        }


        if(inventoryDto.getQuantity()!=null) inventory.setQuantity(inventoryDto.getQuantity());
        if(inventoryDto.getReserved()!=null) inventory.setReserved(inventoryDto.getReserved());

        inventory=this.inventoryRepository.save(inventory);
        logger.info("Successfully updated the inventory.....");
        return InventoryMapper.getInventoryDto(inventory);
    }

    public Inventory_Dto getInventoryById(Integer id){
        Inventory inventory=this.inventoryRepository.getInventoryById(id);

        if(inventory==null){
            logger.info("Inventory with {id="+id+"} does not exist cannot get....");
            throw new InventoryProcessingException("Inventory with {id="+id+"} does not exist cannot get....");
        }

        logger.info("Successfully got the inventory.....");
        return InventoryMapper.getInventoryDto(inventory);
    }

    //this will be responsible for reserving the stock
    public void reserveStock(Integer productId,Integer quantity){

        logger.info("Got the request for reserving the stock....");
        int count=this.inventoryRepository.reserveStockInInventory(productId,quantity);

        if(count==0){
            logger.info("Out of stock for the product......");
            throw new InventoryProcessingException("Out of stock for product with {id="+productId+"}....");
        }

        logger.info("Successfully reserved the stock.......");

    }
}
