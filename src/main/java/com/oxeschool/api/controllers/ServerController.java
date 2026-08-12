package com.oxeschool.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServerController {

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

}