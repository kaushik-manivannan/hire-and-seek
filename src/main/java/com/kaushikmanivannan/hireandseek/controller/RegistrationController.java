package com.kaushikmanivannan.hireandseek.controller;

import com.kaushikmanivannan.hireandseek.dto.RegistrationDTO;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService theUserService) {
        userService = theUserService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        if (!model.containsAttribute("registrationDTO")) {
            model.addAttribute("registrationDTO", new RegistrationDTO());
        }
        return "registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes,
            Model model){

        String email = registrationDTO.getEmail();
        String phone = registrationDTO.getPhone();

        // Form validation
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDTO", bindingResult);
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/register";  // Redirect back to the registration form
        }

        // Check the database if user already exists with the same email or phone
        User existingByEmail = userService.findByEmail(email);
        User existingByPhone = userService.findByPhone(phone);

        boolean duplicateFound = false;

        if (existingByEmail != null) {
            redirectAttributes.addFlashAttribute("emailError", "Email already exists. Please try a different email.");
            duplicateFound = true;
        }

        if (existingByPhone != null) {
            redirectAttributes.addFlashAttribute("phoneError", "Phone number already exists. Please try a different phone number.");
            duplicateFound = true;
        }

        if (duplicateFound) {
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);  // Add back the form data
            return "redirect:/register";  // Stay on the registration form to show errors
        }

        // Create user account and store in the database
        userService.save(registrationDTO);
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");

        // Place user in the session for later use
        session.setAttribute("user", registrationDTO);

        return "redirect:/";
    }
}
