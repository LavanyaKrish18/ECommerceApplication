package org.example.productcatalogservice.repos;

import jakarta.transaction.Transactional;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test // whatever saved or updated in DB will be rolled back as it is a test class
    @Transactional // execute all or nothing
    public void demonstateLoading(){
        Optional<Category> optionalCategory = categoryRepo.findById(2L);

        if(optionalCategory.isPresent()){
            Category c = optionalCategory.get();
//            for (Product product : c.getProducts()){
//                System.out.println(product.getName() + product.getCategory().getName());
//            }
        }

        //Fetch type of Category class - products field is Lazy
            // If asked for products 2 queries - fetch category and products tables
            // If not asked for products 1 query - fetch category table
        //Fetch type of Category class - products field is Eager - Run 1 left join query
            // If asked for products 2 queries - fetch category and products tables
            // If not asked for products 2 queries - fetch category and products tables

    }

}