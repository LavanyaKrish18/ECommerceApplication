package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    //Jaxon responsible for converting object to Json and vice versa
    //ObjectMapper library used for Jaxon in Java

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

    @PostMapping("/products")
    //public Product createProduct(Product product) { // param:product will not be considered as input
    public Product createProduct(@RequestBody Product product) {
        return product;
    }
}
