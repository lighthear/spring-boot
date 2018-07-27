package com.example.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {
    @RequestMapping(value = "/hello")
    public String sayHello(HttpServletRequest request) {
        String pin = request.getParameter("pin");
        int channel = Integer.parseInt(request.getParameter("channel"));
        return pin+channel;
    }
}
