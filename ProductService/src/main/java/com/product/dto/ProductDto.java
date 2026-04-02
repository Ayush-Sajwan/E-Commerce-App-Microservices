package com.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public class ProductDto {


    private Integer id;

    @JsonProperty("name")
    @NotNull(groups = {ProductDtoGroups.createProductDto.class},
    message = "Product Name cannot be null.....")
    private String name;

    @JsonProperty("description")
    @NotNull(groups = {ProductDtoGroups.createProductDto.class},
            message = "Product Description cannot be null.....")
    private String description;

    @JsonProperty("price")
    @NotNull(groups = {ProductDtoGroups.createProductDto.class},
            message = "Product price cannot be null.....")
    private BigDecimal price;

    @JsonProperty("category_id")
    @NotNull(groups = {ProductDtoGroups.createProductDto.class},
            message = "Product category id cannot be null.....")
    private Integer category_id;

    @JsonProperty("brand")
    @NotNull(groups = {ProductDtoGroups.createProductDto.class},
            message = "Product brand cannot be null.....")
    private String brand;

    public byte[] image;

    @JsonProperty("seller_id")
    private Integer seller_id;

    public ProductDto(){
        System.out.println("Product dto is created.....");
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public byte[] getImage() {
        return image;
    }

    public Integer getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Integer seller_id) {
        this.seller_id = seller_id;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category_id=" + category_id +
                ", brand='" + brand + '\'' +
                ", seller_id=" + seller_id +
                '}';
    }
}
