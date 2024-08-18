package org.example.productcatalogservice.services;

import lombok.NonNull;
import org.example.productcatalogservice.clients.FakeStoreApiClient;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired //create constructor or use this [both are same] //singleton object
    private FakeStoreApiClient fakeStoreApiClient;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProductById(id);
        return productConverter.getProduct(fakeStoreProductDto);
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
}
