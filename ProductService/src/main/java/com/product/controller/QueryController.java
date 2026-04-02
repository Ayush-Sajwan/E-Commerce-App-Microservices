package com.product.controller;

import com.product.dto.ProductDto;
import com.product.response.ApiResponse;
import com.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    public final static Logger logger= LoggerFactory.getLogger(QueryController.class);
    private ProductService productService;

    public QueryController(ProductService productService){
      this.productService=productService;
    }

    @GetMapping("/product")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getPProduct(@RequestParam("categoryId") Integer categoryId,
                                                                     @RequestParam(value = "cursorId",required = false) Integer cursorId,
                                                                     @RequestParam(value = "size",required = false) Integer size){

        logger.info("Got the request: {categoryId:"+categoryId+" ,cursorId:"+cursorId);

        if(size==null) size=5;

        List<ProductDto> productDtos=this.productService.getProductsByCategory(categoryId,cursorId,size);

        logger.info("Returning the response.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully fetched the products.....",productDtos));

    }

    @GetMapping("/product/search")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductByName(@RequestParam("name") String name,
                                                                          @RequestParam(value = "cursorId",required = false) Integer cursorId,
                                                                          @RequestParam("size") Integer size){


        logger.info("Got the request for getting the product.....:{ name:"+name+" ,size:"+size+" }");

        if(size==null) size=5;

        List<ProductDto> productDtos=this.productService.getProductsByName(name,cursorId,size);

        logger.info("Returning the response.......");
        return ResponseEntity.status(HttpStatusCode.valueOf(200))
                .body(new ApiResponse<>("Successfully fetched the products.....",productDtos));

    }
}
