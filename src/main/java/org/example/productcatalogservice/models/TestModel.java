package org.example.productcatalogservice.models;

//Created just to test DB migration

import jakarta.persistence.Entity;

@Entity
public class TestModel extends BaseModel{
    // v1 - no fields [init-schema], v2 - 2 fields[update-schema], v3 - removed 1 field[update-schema2]
    // Run update-schema2.sql to remove the field from DB as application.properties has ddl = update
    private String textField;

    //private Integer numField;
}
