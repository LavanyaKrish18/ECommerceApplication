package org.example.productcatalogservice.services;

import lombok.NonNull;
import org.example.productcatalogservice.clients.FakeStoreApiClient;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service("fkps")
@Primary
public class FakeStoreProductService implements IProductService {

    @Autowired //create constructor or use this [both are same] //singleton object
    private FakeStoreApiClient fakeStoreApiClient;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Product getProductById(Long id) {
        // Use Redis Cache - Algo
//         Check if the product is present in cache
//                if found, return product
//                else
//                    request from fakestore api
//                    persist in cache
//                    return product

        // Install Redis to see the results [For windows, install docker then only can install redis]

        // Homework - Spring Cloud [API G/W, LB, Log..] - Redis Cache [Looks good in Resume]
        // Add a member isPopular in Product class
        // Only if popular follow this algo
        // else use fakestore only

        FakeStoreProductDto fakeStoreProductDto = null;

        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get("PRODUCTS__", id);

        if (fakeStoreProductDto != null) {
            System.out.println("FOUND IN CACHE");
            return productConverter.getProduct(fakeStoreProductDto);
        }

        fakeStoreProductDto = fakeStoreApiClient.getProductById(id);

        if (fakeStoreProductDto != null) {
            System.out.println("FOUND IN FAKESTORE");
            redisTemplate.opsForHash().put("PRODUCTS__", id, fakeStoreProductDto);
            return productConverter.getProduct(fakeStoreProductDto);
        }

        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] responseProducts = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        if (responseProducts != null) {
            for (FakeStoreProductDto fakeStoreProductDto : responseProducts) {
                products.add(productConverter.getProduct(fakeStoreProductDto));
            }
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto responseFakeStoreProductDto = fakeStoreApiClient.createProduct(product);
        return productConverter.getProduct(responseFakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto responseFakeStoreProductDto = fakeStoreApiClient.replaceProduct(id, product);
        return productConverter.getProduct(Objects.requireNonNull(responseFakeStoreProductDto));
    }

    @Override
    public Product replacePartialProduct(Long id, Product product) {
        FakeStoreProductDto responseFakeStoreProductDto = fakeStoreApiClient.replacePartialProduct(id, product);
        return productConverter.getProduct(Objects.requireNonNull(responseFakeStoreProductDto));
    }

    @Override
    public Product deleteProduct(Long id) {
        FakeStoreProductDto responseFakeStoreProductDto = fakeStoreApiClient.deleteProduct(id);
        return productConverter.getProduct(Objects.requireNonNull(responseFakeStoreProductDto));
    }

    @Override
    public Product getProductBasedOnUserRole(Long userId, Long productId) {
        return null;
    }
}
