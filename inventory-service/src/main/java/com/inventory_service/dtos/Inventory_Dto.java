package com.inventory_service.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class Inventory_Dto {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("productId")
    @NotNull(groups = {InventoryGroups.createInventory.class,InventoryGroups.updateInventory.class},
    message = "Product Id cannot be null")
    private Integer product_id;

    @JsonProperty("quantity")
    @NotNull(groups = {InventoryGroups.createInventory.class},
    message = "Quantity cannot be null")
    private Integer quantity;

    @JsonProperty("reserved")
    @NotNull(groups = {InventoryGroups.createInventory.class},
    message ="Reserved Quantity Cannot be null")
    private Integer reserved;


    public Inventory_Dto(){
        System.out.println("Inventory dto is created.....");

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }
}
