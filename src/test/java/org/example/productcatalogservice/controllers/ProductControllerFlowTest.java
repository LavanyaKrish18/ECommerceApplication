package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductControllerFlowTest {

    @Autowired
    private ProductController productController;

    @Test
    public void Test_Create_Replace_GetProductById_WithStub_RunsSuccessfully() {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("IPhone");

        //Act
        ProductDto createdProductDto = productController.createProduct(productDto);
        ResponseEntity<ProductDto> productDtoResponseEntity1 = productController.getProductById(1L);
        productDto.setName("Samsung");

        ProductDto replacedProductDto = productController.replaceProduct(1L, productDto);
        ResponseEntity<ProductDto> productDtoResponseEntity2 = productController.getProductById(1L);

        //Assert
        assertEquals("IPhone", productDtoResponseEntity1.getBody().getName());
        assertEquals("Samsung", productDtoResponseEntity2.getBody().getName());

    }
}
