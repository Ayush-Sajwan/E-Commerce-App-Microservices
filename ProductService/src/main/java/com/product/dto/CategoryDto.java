package com.product.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CategoryDto {


    @JsonIgnore
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    @NotNull(groups = {CategoryDtoGroups.createCategory.class},
            message = "Category Name cannot be null")
    private String name;

    @JsonProperty("description")
    @NotNull(groups = {CategoryDtoGroups.createCategory.class},
            message = "Category Description cannot be null")
    private String description;

    public CategoryDto(){
        System.out.println("Category dto is created");
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

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
