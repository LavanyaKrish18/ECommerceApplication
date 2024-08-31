package org.example.productcatalogservice.controllers;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.example.productcatalogservice.services.ProductConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    //@Qualifier("sps")
    private IProductService productService;

    @Autowired
    private ProductConverter productConverter;

    @GetMapping
    public List<ProductDto> getProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(productConverter.getProductDto(product));
        }
        return productDtos;
    }

//    @GetMapping("/products/{id}")
//    public ProductDto getProductById(@PathVariable("id") Long productId) {
//        Product product = productService.getProductById(productId);
//        return productConverter.getProductDto(Objects.requireNonNull(product));
//    }

    // To override dispatcher servlet's response
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if (productId == 0){
            throw new IllegalArgumentException("Product id cannot be Zero");
        }else if(productId < 0){
            throw new IllegalArgumentException("Invalid product id");
        }
        Product product = productService.getProductById(productId);
        ProductDto productDto = productConverter.getProductDto(product);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Response edited by", "Lavs");
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product responseProduct = productService.replaceProduct(id, productConverter.getProduct(productDto));
        return productConverter.getProductDto(responseProduct);
    }

    //TODO : FakeStoreAPI - HttpClientErrorException thrown
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product responseProduct = productService.createProduct(productConverter.getProduct(productDto));
        return productConverter.getProductDto(responseProduct);
    }

    // TODO : FakeStoreAPI - ResourceAccessException thrown
    // StorageProduct Service - Change of Name and price fields is only implemented
    @PatchMapping("{id}")
    public ProductDto replacePartialProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product responseProduct = productService.replacePartialProduct(id, productConverter.getProduct(productDto));
        return productConverter.getProductDto(responseProduct);
    }

    // TODO : FakeStoreAPI - Expected ? Deleted product is printed, still i can get the product in GET Api request
    @DeleteMapping("{id}")
    public ProductDto deleteProduct(@PathVariable Long id) {
        Product responseProduct = productService.deleteProduct(id);
        return productConverter.getProductDto(responseProduct);
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
