package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    ProductController productController;

    @MockBean
    IProductService productService;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Test
    public void Test_GetProductById_WithValidProductId_ReturnsProductSuccessfully() {
        //Arrange
        Long productId = 2L;
        Product product = new Product();
        product.setId(productId);
        product.setDescription("Distractor");
        product.setImageUrl("https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg");
        product.setName("Samsung s25 ultra");
        product.setPrice(150000.0);
        Category category = new Category();
        category.setId(3L);
        category.setName("Electronics");
        product.setCategory(category);
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        ResponseEntity<ProductDto> response = productController.getProductById(productId);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(response.getBody().getId(), productId);
        assertEquals("Distractor", response.getBody().getDescription());
        assertEquals("Samsung s25 ultra", response.getBody().getName());// sameway all fields can be checked
        assertEquals("Lavs", response.getHeaders().getFirst("Response edited by"));
        //how many interaction with my mock
        verify(productService,times(1)).getProductById(productId);
    }

    @Test
    public void Test_GetProductById_WithInvalidProductId_ThrowsIllegalArgumentException() {
        //Act and Assert
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> productController.getProductById(-1L));

        assertEquals("Invalid product id", e.getMessage());

        verify(productService,times(0)).getProductById(-1L);
    }

    @Test
    public void Test_GetProductById_WithValidProductId_ThrowsRuntimeException() {
        //Arrange
        Long productId = 2L;
        when(productService.getProductById(productId)).thenThrow(new RuntimeException("Product not found"));

        //Act and Arrange
        Exception e = assertThrows(RuntimeException.class, () -> productController.getProductById(productId));
        assertEquals("Product not found", e.getMessage());
        verify(productService,times(1)).getProductById(productId);
    }

    @Test
    public void Test_CreateProduct_WithValidPayload_CreatesProductSuccessfully() {
        ProductDto productDto = new ProductDto();
        productDto.setId(2L);
        productDto.setName("Samsung s25 ultra");
        productDto.setPrice(150000.0);

        Product product = new Product();
        product.setId(2L);
        product.setName("Samsung s25 ultra");
        product.setPrice(150000.0);

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ProductDto productDtoResult = productController.createProduct(productDto);

        assertNotNull(productDtoResult);
        assertEquals("Samsung s25 ultra", productDtoResult.getName());
        //assertEquals(productDtoResult, productDto); cannot directly compare 2 objects
    }


    @DisplayName("ProductId passed is 1. Expecting ProductService also to be called with productId 1. If this fails, ProductService is not called with productId as 1")
    @Test
    public void Test_GetProductById_ServiceCalledWithExpectedArguments_RunsSuccessfully() throws Exception {
        //Arrange
        Long productId = 10L;
        Product product = new Product();
        product.setName("Ice Cream");
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        productController.getProductById(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
    }
    // Homework : write Service layer and Client UT and all other fns here or in API test
}