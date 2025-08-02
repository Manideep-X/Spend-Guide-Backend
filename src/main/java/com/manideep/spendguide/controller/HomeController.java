package com.manideep.spendguide.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/health")
public class HomeController {

    // Demo controller to check if the application is running
    @GetMapping("")
    public String getAppHealth() {
        return "Application is UP and RUNNING !";
    }
    

}
