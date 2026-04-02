package com.product.service;

import com.product.dto.CategoryDto;
import com.product.entity.Category;
import com.product.exceptions.CategoryProcessingError;
import com.product.mapper.CategoryMapper;
import com.product.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    public static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        logger.info("Category Service is created.......");
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = CategoryMapper.getCategoryEntity(categoryDto);

        category = categoryRepository.save(category);
        logger.info("Category successfully saved.....");

        return CategoryMapper.getCategoryDto(category);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto,int id){

        Category category=categoryRepository.getCategoryById(id);
        if(category==null) {
            logger.info("Could not find category with the id:"+id);
            throw new CategoryProcessingError("Category with id:"+id+" not found.....");
        }

        if(categoryDto.getName()!=null) category.setName(categoryDto.getName());
        if(categoryDto.getDescription()!=null) category.setDescription(categoryDto.getDescription());

        category=categoryRepository.save(category);
        logger.info("Category is successfully updated......");

        return CategoryMapper.getCategoryDto(category);
    }

    public CategoryDto getCategoryById(int id){

        Category category=categoryRepository.getCategoryById(id);
        if(category==null) {
            logger.info("Could not find category with the id:"+id);
            throw new CategoryProcessingError("Category with id:"+id+" not found.....");
        }

        return CategoryMapper.getCategoryDto(category);
    }


    public List<CategoryDto> getAllCategories(){

        List<Category> categories=categoryRepository.findAll();

        List<CategoryDto> categoryDtos=categories.stream()
                .map(category -> CategoryMapper.getCategoryDto(category))
                .toList();

        return categoryDtos;
    }
}
