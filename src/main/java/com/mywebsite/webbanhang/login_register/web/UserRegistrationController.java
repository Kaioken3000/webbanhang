package com.mywebsite.webbanhang.login_register.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mywebsite.webbanhang.login_register.service.UserService;
import com.mywebsite.webbanhang.login_register.web.dto.UserRegistrationDto;

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
        return "registration2";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto){
        userService.save(registrationDto, 1);
        return "redirect:/registration?success";
    }
}
