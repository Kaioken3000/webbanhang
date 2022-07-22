package com.mywebsite.webbanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mywebsite.webbanhang.model.dto.UserRegistrationDto;
import com.mywebsite.webbanhang.service.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    private UserService userService;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto(){
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        return registrationDto;
    }

    @GetMapping
    public String showRegistrationform(){
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto, 1);
        return "redirect:/registration?success";
    }
}
