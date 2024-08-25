package org.example.productcatalogservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.example.productcatalogservice.services.ProductConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
