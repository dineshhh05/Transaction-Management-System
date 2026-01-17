package com.dinesh.tms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrintHome {
    
    @GetMapping("/home")
    public String home() {
        return ("This is the home page of the TMS System");
    }
    

}
