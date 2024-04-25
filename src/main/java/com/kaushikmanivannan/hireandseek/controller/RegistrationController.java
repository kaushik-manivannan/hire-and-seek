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
        // Ensure the RegistrationDTO is available to the form to hold form data.
        if (!model.containsAttribute("registrationDTO")) {
            model.addAttribute("registrationDTO", new RegistrationDTO());
        }
        return "registration-form"; // Directs the user to the registration form view.
    }

    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(
            @Valid @ModelAttribute("registrationDTO") RegistrationDTO registrationDTO,
            BindingResult bindingResult,
            HttpSession session,
            RedirectAttributes redirectAttributes){

        // Extract email and phone from the DTO.
        String email = registrationDTO.getEmail();
        String phone = registrationDTO.getPhone();

        // Form validation check for errors.
        if (bindingResult.hasErrors()){
            // If errors, redirect back to the registration form with error details.
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registrationDTO", bindingResult);
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/register";
        }

        // Check if user already exists with the same email or phone.
        User existingByEmail = userService.findByEmail(email);
        User existingByPhone = userService.findByPhone(phone);

        boolean duplicateFound = false;

        // Handling duplicates for email.
        if (existingByEmail != null) {
            redirectAttributes.addFlashAttribute("emailError", "Email already exists. Please try a different email.");
            duplicateFound = true;
        }

        // Handling duplicates for phone.
        if (existingByPhone != null) {
            redirectAttributes.addFlashAttribute("phoneError", "Phone number already exists. Please try a different phone number.");
            duplicateFound = true;
        }

        // In case of duplicates, redirect back to form with errors shown.
        if (duplicateFound) {
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            return "redirect:/register";
        }

        // Save the new user in the database.
        userService.save(registrationDTO);
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");

        // Store user in session after successful registration.
        session.setAttribute("user", registrationDTO);

        // Redirect to the login page after successful registration.
        return "redirect:/";
    }
}