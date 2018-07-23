package com.example.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping(value = "/hello")
    public String sayHello() {
        return "Hello Spring Boot! + Hello！";
    }
}
