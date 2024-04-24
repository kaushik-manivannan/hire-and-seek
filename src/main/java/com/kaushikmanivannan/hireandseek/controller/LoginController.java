package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.dto.LoginDTO;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.UserService;
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
