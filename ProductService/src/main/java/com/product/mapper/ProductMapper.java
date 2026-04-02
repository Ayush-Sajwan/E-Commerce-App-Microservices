package com.product.mapper;


import com.product.dto.ProductDto;
import com.product.entity.Product;

public class ProductMapper {

    public static Product getProduct(ProductDto productDto){

        Product product=new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setCategory_id(productDto.getCategory_id());
        product.setSeller_id(productDto.getSeller_id());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());

        return product;

    }

    public static ProductDto getProductDto(Product product){

        ProductDto productDto=new ProductDto();

        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setBrand(product.getBrand());
        productDto.setCategory_id(product.getCategory_id());
        productDto.setSeller_id(product.getSeller_id());
        productDto.setPrice(product.getPrice());
        productDto.image=product.getImage();

        return productDto;
    }

}
