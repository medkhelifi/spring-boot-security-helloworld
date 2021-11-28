package com.medkhelifi.tutorials.springsecurityhelloworld.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class IndexController {

    @GetMapping(value = "")
    public ResponseEntity<Object> index()  {
        return new ResponseEntity<>("Je suis connecté", HttpStatus.OK);
    }
}