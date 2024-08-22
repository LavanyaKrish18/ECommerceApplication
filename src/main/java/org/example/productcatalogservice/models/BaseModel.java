package org.example.productcatalogservice.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    private Long id;

    private Date createdAt;

    private Date lastUpdatedAt;

    private Status status; // tells the status of the class. if deleted - inactive.

    //private UUID uuid = UUID.randomUUID(); // THis is recommended not using this as fakestore has int/long id
}
