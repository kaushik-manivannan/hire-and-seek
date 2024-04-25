package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.dto.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String showLoginForm(Model model){
        model.addAttribute("loginDTO", new LoginDTO());
        return "login-form";
    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage(){
        return "access-denied";
    }

}
