package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dto.RegistrationDTO;
import com.kaushikmanivannan.hireandseek.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(RegistrationDTO registrationDTO);
    User findByEmail(String email);
    User findByPhone(String phone);
}
