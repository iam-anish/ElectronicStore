package com.lcwd.electronicstore2.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


    @CrossOrigin
    @RestController
    @RequestMapping({"/test"})
    public class HomeController {
        public HomeController() {
        }

        @GetMapping
        public String testing() {
            return "Welcome to electronic  store";
        }
    }

