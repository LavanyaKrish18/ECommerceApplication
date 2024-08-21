package org.example.productcatalogservice.repos;

import org.example.productcatalogservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    //File creation is mandatory bcoz we will interact with JpaRepository using this class only
    //Declaring just for reference. Can be commented
    Product save(Product product);

    Optional<Product> findById(Long id);
}
