package org.example.productcatalogservice.services;

import org.example.productcatalogservice.dtos.CategoryDto;
import org.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.example.productcatalogservice.dtos.ProductDto;
import org.example.productcatalogservice.models.Category;
import org.example.productcatalogservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        //category.setId(fakeStoreProductDto.getId()); fakeStoreDto doesn't have id for category
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    public Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setIsPrime(productDto.getIsPrime()); // Remove after test
        if (productDto.getCategoryDto() != null) {
            Category category = new Category();
            category.setId(productDto.getCategoryDto().getId());
            category.setName(productDto.getCategoryDto().getName());
            product.setCategory(category);
        }
        return product;
    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setIsPrime(product.getIsPrime()); // Remove after test
        Category category = product.getCategory();
        if (category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            productDto.setCategoryDto(categoryDto);
        }
        return productDto;
    }

    public FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            //fakeStoreProductDto.setId(product.getCategory().getId()); fakeStoreDto doesn't have id for category
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }
}
