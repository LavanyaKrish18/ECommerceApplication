package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.UserDto;
import org.example.productcatalogservice.models.Product;
import org.example.productcatalogservice.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service("sps")
//@Primary
public class StorageProductService implements IProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        Product responseProduct = null;
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            product.setId(existingProduct.getId()); // Preserve the original ID
            responseProduct = productRepo.save(product);      // Save the new product
        }
        return responseProduct;
    }

    @Override
    public Product replacePartialProduct(Long id, Product product) {
        Product responseProduct = null;
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            if (product.getName() != null) {
                existingProduct.setName(product.getName());
            }
            if (product.getPrice() != null) {
                existingProduct.setPrice(product.getPrice());
            }
            // Add other fields similarly
            responseProduct = productRepo.save(existingProduct);
        }
        return responseProduct;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product responseProduct = null;
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            responseProduct = optionalProduct.get();
            productRepo.deleteById(id);
        }
        return responseProduct;
    }

    @Override
    public Product getProductBasedOnUserRole(Long userId, Long productId) {
        Product product = productRepo.findById(productId).get();

        UserDto userDto = restTemplate.getForEntity("http://userauthenticationservice/user/{userId}", UserDto.class, userId).getBody();

        if(userDto != null) {
            // Homework - Service Discovery class - Eureka server class - Spring Cloud
            // If Product is a private product
            // If yes, Check for the role if he is the admin or owner of the product
            // Then only return product, else return null
            System.out.println("Received User");
            return product;
        }
        else {
            return null;
        }
    }

}
