package com.product.service;

import com.product.dto.ProductDto;
import com.product.entity.Product;
import com.product.exceptions.ProductNotFoundException;
import com.product.mapper.ProductMapper;
import com.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final Logger logger= LoggerFactory.getLogger(ProductService.class);
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        logger.info("Product Service object is created.....");
        this.productRepository=productRepository;
    }

    public ProductDto createProduct(ProductDto productDto){

        Product product= ProductMapper.getProduct(productDto);

        product=this.productRepository.save(product);
        logger.info("Product Successfully saved.....");

        return productDto;
    }


    public ProductDto getProductById(int id){
        logger.info("Getting the product by id.....");

        Product product=this.productRepository.getProductById(id);
        logger.info("Successfully got the product.....");

        return ProductMapper.getProductDto(product);
    }

    public ProductDto patchProductById(ProductDto productDto,int id){

        Product product=productRepository.getProductById(id);
        if(product==null){
            logger.info("Product with id-->"+id+" not found");
            throw new ProductNotFoundException("Product with product_id:{"+id+"} not found.....");
        }

        logger.info("Product found with id..");

        //now checking all the fields that are not null and setting in the details
        if (productDto.getName() != null)
            product.setName(productDto.getName());

        if (productDto.getDescription() != null)
            product.setDescription(productDto.getDescription());

        if (productDto.getPrice() != null)
            product.setPrice(productDto.getPrice());

        if (productDto.getCategory_id() != null)
            product.setCategory_id(productDto.getCategory_id());

        if (productDto.getBrand() != null)
            product.setBrand(productDto.getBrand());

        if (productDto.getImage() != null)
            product.setImage(productDto.getImage());

        if (productDto.getSeller_id() != null)
            product.setSeller_id(productDto.getSeller_id());


        product=productRepository.save(product);

        logger.info("Successfully updated the product details.....");

        return ProductMapper.getProductDto(product);
    }


    public List<ProductDto> getProductsByCategory(Integer categoryId,Integer cursorId,Integer size){

        logger.info("Getting all the products by category.....");

        List<Product> products=this.productRepository.getProductsByCategory(categoryId,cursorId,size);

        List<ProductDto> productDtos=products.stream()
                .map(product -> ProductMapper.getProductDto(product))
                .toList();

        logger.info("Returning the products........");

        return productDtos;
    }


    public List<ProductDto> getProductsByName(String name,Integer cursorId,Integer size){

        logger.info("Getting all the products by name.....");

        List<Product> products=this.productRepository.getProductsByName(name,cursorId,size);

        List<ProductDto> productDtos=products.stream()
                .map(product -> ProductMapper.getProductDto(product))
                .toList();

        logger.info("Returning the products........");

        return productDtos;
    }

}
