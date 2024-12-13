package org.example.productcatalogservice.stubs;

import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.services.IProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceStub implements IProductService {

    Map<Long, Product> productMap;

    public ProductServiceStub() {
        this.productMap = new HashMap<>();
    }

    @Override
    public Product getProductById(Long id) {
        return productMap.get(id);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.addAll(productMap.values());
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        productMap.put(product.getId(), product);
        return productMap.get(product.getId());
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        productMap.put(id, product);
        return productMap.get(id);
    }

    @Override
    //Implementation done only for Name and Price fields
    public Product replacePartialProduct(Long id, Product product) {
        Product existingProduct = productMap.get(id);
        if (existingProduct != null) {
            if (!existingProduct.getName().equals(product.getName())) {
                existingProduct.setName(product.getName());
            }
            if(!existingProduct.getPrice().equals(product.getPrice())) {
                existingProduct.setPrice(product.getPrice());
            }
            //Other fields can be added
        }
        productMap.put(id, existingProduct);
        return productMap.get(id);

    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDeleted = productMap.get(id);
        productMap.remove(id);
        return productDeleted;
    }

    @Override
    public Product getProductBasedOnUserRole(Long userId, Long productId) {
        return null;
    }
}
