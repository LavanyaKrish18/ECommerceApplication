package org.example.productcatalogservice.TableInheritanceExamples.MappedSuperClass;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class User {

    @Id
    private Long id;

    private String name;
}
