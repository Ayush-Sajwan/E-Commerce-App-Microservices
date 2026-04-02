package com.product.controller;

import com.product.dto.CategoryDto;
import com.product.dto.CategoryDtoGroups;
import com.product.response.ApiResponse;
import com.product.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private static final Logger logger= LoggerFactory.getLogger(CategoryController.class);
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService=categoryService;
        logger.info("Category Controller is created......");
    }

    @PostMapping("/product/category/")
    public ResponseEntity<ApiResponse<CategoryDto>> createCategory(@Validated(CategoryDtoGroups.createCategory.class)
                                                                       @RequestBody CategoryDto categoryDto){

        logger.info("Request to create category is received: "+categoryDto);

        categoryDto=categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(
                        new ApiResponse<CategoryDto>("Category Successfully created.....",categoryDto)
                );
    }

    @PatchMapping("/product/category/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer id){

        logger.info("Request to updated category is received: "+categoryDto);

        categoryDto=categoryService.updateCategory(categoryDto,id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(
                        new ApiResponse<CategoryDto>("Category Successfully updated.....",categoryDto)
                );
    }

    @GetMapping("/product/category/{id}")
    public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable Integer id){

        logger.info("Request to get category is received ...");

        CategoryDto categoryDto=categoryService.getCategoryById(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(
                        new ApiResponse<CategoryDto>("Category Successfully fetched.....",categoryDto)
                );
    }


    @GetMapping("/product/category/all")
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories(){

        logger.info("Request to get all categories is received.....");

        List<CategoryDto> list=categoryService.getAllCategories();

        logger.info("Returning all the categories.......");

        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully fetched all the categories.."
                ,list));
    }

}
