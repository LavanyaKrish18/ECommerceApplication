package org.example.productcatalogservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("{id}")
    public String getMessage(@PathVariable int id) {
        if (id <= 0){
            throw new IllegalArgumentException("Test - id must be greater than 0");
        }
        return "Hello Guys, Started working on Project Module";
    }
}
