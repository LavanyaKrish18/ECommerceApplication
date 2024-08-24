package org.example.productcatalogservice.repos;

import jakarta.transaction.Transactional;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepoTest {

    @Autowired
    private CategoryRepo categoryRepo;

    @Test // whatever saved or updated in DB will be rolled back as it is a test class
    @Transactional // execute all or nothing
    public void demonstrateLoading() {
        Optional<Category> optionalCategory = categoryRepo.findById(2L);

        if (optionalCategory.isPresent()) {
            Category c = optionalCategory.get();
//            for (Product product : c.getProducts()){
//                System.out.println(product.getName() + product.getCategory().getName());
//            }
        }
    }

        @Test
        @Transactional
        public void demonstrateFetchModeFetchType() {
        //used to fill FetchMode-FetchType-Results table
            Optional<Category> optionalCategory = categoryRepo.findById(2L);

            if (optionalCategory.isPresent()) {
                Category c = optionalCategory.get();
                for (Product product : c.getProducts()) {
                    System.out.println(product.getName() + product.getCategory().getName());
                }
            }
        }

    @Test
    @Transactional
    // One of the solutions to nPlusOneProblem is SubSelect
    // 2nd solution - use SELECT with Batch size
    // JOIN gives same result as SELECT which causes nPlusOneProblem
    // As per chatGPT, join can also reduce to 1 query. but not working in our example
    public void nPlusOneProblem () { // demonstrateFetchModeSubSelect
        // Try first LAZY, SELECT - 1 query to fetch all category and
            // N query to getProducts for each category. N = category. nPlusOneProblem
        //Try LAZY, SUBSELECT
        List<Category> categoryList = categoryRepo.findAll();
        for(Category category : categoryList) {
            for (Product product : category.getProducts()) {
                System.out.println(product.getName() + product.getCategory().getName());
            }
        }
    }
}