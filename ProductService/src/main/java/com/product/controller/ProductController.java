package com.product.controller;

import com.product.dto.ProductDto;
import com.product.dto.ProductDtoGroups;
import com.product.response.ApiResponse;
import com.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ProductController {

    private final Logger logger= LoggerFactory.getLogger(ProductController.class);
    private ProductService productService;


    public ProductController(ProductService productService){
        logger.info("Product Controller successfully created.....");
        this.productService=productService;
    }

    @PostMapping("/product")
    public ResponseEntity<ApiResponse<String>> createProduct(@Validated(ProductDtoGroups.createProductDto.class)
                                                                 @ModelAttribute ProductDto productDto, @RequestParam MultipartFile image) throws IOException {

        logger.info("Post request for object creation received..");
        logger.info("Object-->"+productDto);

        //setting the image to bytes
        productDto.image=image.getBytes();

        this.productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(new ApiResponse<String>("Product Successfully created..","Product creation done"));
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable int id){

        logger.info("Request to get product by id received.....");

        ProductDto productDto=this.productService.getProductById(id);
        logger.info("Successfully got the product...."+productDto);

        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(new ApiResponse<ProductDto>("Successfully fetched the product",productDto));


    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ApiResponse<ProductDto>> patchProduct(@ModelAttribute ProductDto productDto,
                                                                @RequestParam(required = false) MultipartFile file,
                                                                @PathVariable int id) throws IOException{

        logger.info("Request is received....."+productDto);
        if(file!=null) productDto.image=file.getBytes();

        productDto=productService.patchProductById(productDto,id);
        return ResponseEntity.status(HttpStatusCode.valueOf(201))
                .body(new ApiResponse<ProductDto>("Successfully updated the product details...",productDto));
    }


}
