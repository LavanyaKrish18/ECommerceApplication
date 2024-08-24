package org.example.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{

    private String name;

    private String description;

    // Fetch type by default is LAZY
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY) //LAZY, EAGER
    @Fetch(FetchMode.SELECT) // SELECT, JOIN, SUBSELECT
    @BatchSize(size = 3) // 1, 6, 3
    private List<Product> products;
}

// BatchSize
//N+1 Problem  => 1 + 6 ,
//if(n == N) -> 1 + 1 n=6 -> 1 + 1
//if(n < N) -> 1 + N/n , n = 3 ,  1+2
//if(n>N) -> 1+1