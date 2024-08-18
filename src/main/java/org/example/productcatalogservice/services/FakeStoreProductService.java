package org.example.productcatalogservice.services;

import lombok.NonNull;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired //create constructor or use this [both are same] //singleton object
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public Product getProductById(Long id) {
        //.class - datatype of response object
        // RestTemplate -  Http client to make 3rd party api http request
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id);
        if (fakeStoreProductDtoResponseEntity.getStatusCode().is2xxSuccessful() && fakeStoreProductDtoResponseEntity.getBody() != null) {
            FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
            return productConverter.getProduct(fakeStoreProductDto);
        }

        return null;
//        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, id).getBody();
//        return productConverter.getProduct(Objects.requireNonNull(fakeStoreProductDto));
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }
}
