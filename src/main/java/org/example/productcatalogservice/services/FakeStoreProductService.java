package org.example.productcatalogservice.services;

import lombok.NonNull;
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

import java.util.ArrayList;
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
        RestTemplate restTemplate = restTemplateBuilder.build();
        //List<FakeStoreProductDto>.class - not possible - check main2.java
        FakeStoreProductDto[] responseProducts = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
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
        return null;
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto input = productConverter.getFakeStoreProductDto(product);
        FakeStoreProductDto responseFakeStoreProductDto = requestForEntity("https://fakestoreapi.com/products/{id}", HttpMethod.PUT, input, FakeStoreProductDto.class, id).getBody();
        return productConverter.getProduct(Objects.requireNonNull(responseFakeStoreProductDto));
    }

    private <T> org.springframework.http.ResponseEntity<T> requestForEntity(java.lang.String url, HttpMethod httpMethod, @org.springframework.lang.Nullable java.lang.Object request, java.lang.Class<T> responseType, java.lang.Object... uriVariables) throws org.springframework.web.client.RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
