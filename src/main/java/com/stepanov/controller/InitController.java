package com.stepanov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InitController {

    @GetMapping("/")
    public String initPageRedirect(){
        return "redirect:/tasks";
    }

}
