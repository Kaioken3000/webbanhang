package com.mywebsite.webbanhang.login_register.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class UserLoginController {

    @GetMapping
    public String showLoginForms(){
        return "login";
    }
}
