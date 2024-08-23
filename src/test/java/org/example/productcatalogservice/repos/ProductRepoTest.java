package org.example.productcatalogservice.repos;

import jakarta.transaction.Transactional;
import org.example.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
    public void testQueries(){
//        List<Product> products = productRepo.findProductByPriceBetween(10000, 200000);
//        List<Product> products = productRepo.findAllByIsPrime(false);
//        List<Product> products = productRepo.findAllByIsPrimeTrue();
        //Test Failed Reason 1. wrong syntax[Have to find by hit and trial
        //Reason 2. JPA couldn't create query for this
//        List<Product> products = productRepo.findAllOrderByPriceDesc(); // Test Failed - wrong syntax
//        List<Product> products = productRepo.findAllByOrderByPriceDesc();
//        for (Product product : products) {
//            System.out.println(product.getName() + " " + product.getPrice() + " " + product.getIsPrime());
//        }

//        String name = productRepo.findProductNameFromProductId(6L);
        String name = productRepo.findCategoryNameFromProductId(6L);
        System.out.println(name);
    }
}