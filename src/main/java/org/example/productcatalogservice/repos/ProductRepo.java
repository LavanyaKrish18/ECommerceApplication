package org.example.productcatalogservice.repos;

import org.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// i don't want to override function, abstract fns. just wanted to use Jpa fns.
// Hence interface created instead of class
// File creation is mandatory bcoz we will interact with JpaRepository using this class only
@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    //Declaring just for reference. Can be commented
    //Product save(Product product);

    //Optional<Product> findById(Long id);

    List<Product> findProductByPriceBetween(double low, double high);

    // added a field isPrime in Product, by default product table will have this column null
    // so modified 2 rows with true and 1 with false
    // executed these below 2 functions and tested.
    // Then commented
    List<Product> findAllByIsPrime(boolean isPrime); //Remove after test

    List<Product> findAllByIsPrimeTrue();// Remove after test

    //List<Product> findAllOrderByPriceDesc();

    List<Product> findAllByOrderByPriceDesc();

    // As we need a particular cell value, JPA will not provide. we need to write custom query
    // Refer below for Named and Positional Association
    @Query("SELECT p.name FROM Product p WHERE p.id = ?1") //Positional
    String findProductNameFromProductId(long id);

    // Named Association
    @Query("SELECT c.name FROM Category c JOIN Product p ON c.id = p.category.id WHERE p.id = :productId")
    String findCategoryNameFromProductId(long productId);
}

//
//PostionalAssociiation
//NamedAssociation
//
//        void foo(int a,int b) {
//        }
//
//        foo(1,2) -pos
//        foo(a:1,b:2) -name