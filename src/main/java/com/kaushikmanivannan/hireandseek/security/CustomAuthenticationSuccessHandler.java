package com.kaushikmanivannan.hireandseek.security;

import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public CustomAuthenticationSuccessHandler(UserService theUserService) {
        userService = theUserService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse
            response, Authentication authentication)
            throws IOException, ServletException {
        String email = authentication.getName();
        User theUser = userService.findByEmail(email);
        HttpSession session = request.getSession();
        session.setAttribute("user", theUser);
        // Forward to Home Page
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
