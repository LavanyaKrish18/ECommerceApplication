package org.example.productcatalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.example.productcatalogservice.services.ProductConverter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class) // To test Apis
public class ProductControllerMvcTests {

    @Autowired
    private MockMvc mockMvc;// helps to test Apis

    @MockBean
    private IProductService productService;

    @MockBean
    private ProductConverter productConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductController productController;

    @Test
    public void Test_GetAllProductsAPI_TestsStatus() throws Exception {

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk());
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Ice Cream");
        Product product2 = new Product();
        product2.setName("Night Pant");
        productList.add(product1);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);

        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Ice Cream");
        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Night Pant");
        productDtoList.add(productDto1);
        productDtoList.add(productDto2);
        when(productConverter.getProductDto(product1)).thenReturn(productDto1);
        when(productConverter.getProductDto(product2)).thenReturn(productDto2);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDtoList)));
        //writeValueAsString(productList)
        // - if we need to use this, add json include non null in product and productDto
        // as we have null in other fields in our application. correct way is to use productDtoList
    }

    @Test
    public void Test_GetAllProductsAPI_TestsContentAndStatus_AssertInJson() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setName("Ice Cream");
        Product product2 = new Product();
        product2.setName("Night Pant");
        productList.add(product1);
        productList.add(product2);
        when(productService.getAllProducts()).thenReturn(productList);

        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto1 = new ProductDto();
        productDto1.setName("Ice Cream");
        ProductDto productDto2 = new ProductDto();
        productDto2.setName("Night Pant");
        productDtoList.add(productDto1);
        productDtoList.add(productDto2);
        when(productConverter.getProductDto(product1)).thenReturn(productDto1);
        when(productConverter.getProductDto(product2)).thenReturn(productDto2);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDtoList)))
                .andExpect(jsonPath("$[0].name").value("Ice Cream"))
                .andExpect(jsonPath("$[1].name").value("Night Pant"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void Test_CreateProductAPI_CreatesProductSuccessfully() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Ice Cream");
        productDto.setId(10L);

        Product product = new Product();
        product.setName("Ice Cream");
        product.setId(10L);

        // TODO : in all 3 mocks, any() is working, exact instance fails the test. Working in previous test
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        when(productConverter.getProduct(any(ProductDto.class))).thenReturn(product);
        when(productConverter.getProductDto(any(Product.class))).thenReturn(productDto);

        //Act and Assert
        mockMvc.perform(post("/products")
                    .content(objectMapper.writeValueAsString(productDto))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

    @Test
    public void Test_CreateProductAPI_CreatesProductSuccessfully_AssertInJson() throws Exception {
        //Arrange
        ProductDto productDto = new ProductDto();
        productDto.setName("Ice Cream");
        productDto.setId(10L);

        Product product = new Product();
        product.setName("Ice Cream");
        product.setId(10L);

        when(productService.createProduct(any(Product.class))).thenReturn(product);
        when(productConverter.getProduct(any(ProductDto.class))).thenReturn(product);
        when(productConverter.getProductDto(any(Product.class))).thenReturn(productDto);

        //Act and Assert
        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)))
                .andExpect(jsonPath("$.name").value("Ice Cream"))
                .andExpect(jsonPath("$.id").value(10L));
    }
}
