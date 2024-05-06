package com.mfsys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/hello")
    public String greetingMessage() {
        return "welcome to api gateway ";
    }

}
