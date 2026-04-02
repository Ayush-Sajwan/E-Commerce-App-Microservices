package com.product.mapper;

import com.product.dto.CategoryDto;
import com.product.entity.Category;

public class CategoryMapper {

    public static CategoryDto getCategoryDto(Category category){

        CategoryDto categoryDto=new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setDescription(category.getDescription());

        return categoryDto;
    }

    public static Category getCategoryEntity(CategoryDto categoryDto){

        Category category=new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return category;
    }
}
