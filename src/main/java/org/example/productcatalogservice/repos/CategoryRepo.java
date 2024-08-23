package org.example.productcatalogservice.repos;

import org.example.productcatalogservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findById(Long id);
}
