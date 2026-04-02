package com.inventory_service.mapper;

import com.inventory_service.dtos.Inventory_Dto;
import com.inventory_service.entity.Inventory;

public class InventoryMapper {

    public static Inventory getInventory(Inventory_Dto inventoryDto){

        Inventory inventory=new Inventory();
        inventory.setId(inventoryDto.getId());
        inventory.setQuantity(inventoryDto.getQuantity());
        inventory.setProduct_id(inventoryDto.getProduct_id());
        inventory.setReserved(inventoryDto.getReserved());

        return inventory;
    }

    public static Inventory_Dto getInventoryDto(Inventory inventory){

        Inventory_Dto inventoryDto=new Inventory_Dto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setQuantity(inventory.getQuantity());
        inventoryDto.setProduct_id(inventory.getProduct_id());
        inventoryDto.setReserved(inventory.getReserved());

        return inventoryDto;
    }
}
