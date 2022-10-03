package com.tilmeez.springboot.thymeleafdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "authentication/fancy-login-copy";
    }

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "authentication/access-denied";
    }
}
