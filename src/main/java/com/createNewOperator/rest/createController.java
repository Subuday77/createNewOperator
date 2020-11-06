package com.createNewOperator.rest;

import com.createNewOperator.beans.Selenium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class createController {

    @Autowired
    Selenium selenium;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "name") String name, @RequestParam(name = "password") String password) {
    return selenium.login(name,password);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOperator(@RequestParam(name="operatorId") long operatorId, @RequestParam(name = "operatorName") String operatorName) {
        return selenium.createOperator(operatorId, operatorName);
    }

}
