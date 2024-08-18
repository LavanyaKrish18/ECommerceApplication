package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.example.productcatalogservice.services.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @GetMapping("/products")
    public List<ProductDto> getProducts() {
        return null;
    }

//    @GetMapping("/products/{id}")
//    public ProductDto getProductById(@PathVariable("id") Long productId) {
//        Product product = productService.getProductById(productId);
//        return productConverter.getProductDto(Objects.requireNonNull(product));
//    }

    // To override dispatcher servlet's response
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        try {
            if (productId <= 0){
                throw new IllegalArgumentException("Invalid product id");
            }

            Product product = productService.getProductById(productId);
            ProductDto productDto = productConverter.getProductDto(product);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("Response edited by", "Lavs");
            return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
            //return new ResponseEntity<>(productDto, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return null;
    }

    /* Old code not valid now
    //Jackson responsible for converting object to Json and vice versa
    //ObjectMapper library used for Jackson in Java

    @GetMapping("/products")
    public List<Product> getProducts() {
        //List<Product> products = new ArrayList<Product>();
        return null;
    }

    @GetMapping("/products/{id}")
    //public Product getProductById( Long id) { //id not considered
    //public Product getProductById(@PathVariable Long productId) { //error - naming different
    //public Product getProductById(@PathVariable Long id) { // correct
    public Product getProductById(@PathVariable("id") Long productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
        //return null;
    }
    // in the response only true will be shown
//    public boolean getProductById(@PathVariable("id") Long productId) {
//        Product product = new Product();
//        product.setId(productId);
//        return true;
//    }

    @PostMapping("/products")
    //public Product createProduct(Product product) { // param:product will not be considered as input
    public Product createProduct(@RequestBody Product product) {
        return product;
    }
    */
}
