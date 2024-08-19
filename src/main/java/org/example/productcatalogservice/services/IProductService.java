package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProductService {

    public Product getProductById(Long id);

    public List<Product> getAllProducts();

    public Product createProduct(Product product);

    public Product replaceProduct(Long id, Product product);

    public Product replacePartialProduct(Long id, Product product);

    public Product deleteProduct(Long id);

}
