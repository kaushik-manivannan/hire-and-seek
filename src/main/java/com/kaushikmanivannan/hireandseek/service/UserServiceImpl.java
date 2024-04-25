package com.kaushikmanivannan.hireandseek.service;

import com.kaushikmanivannan.hireandseek.dao.CandidateDAO;
import com.kaushikmanivannan.hireandseek.dao.EmployerDAO;
import com.kaushikmanivannan.hireandseek.dao.UserDAO;
import com.kaushikmanivannan.hireandseek.dto.RegistrationDTO;
import com.kaushikmanivannan.hireandseek.model.Candidate;
import com.kaushikmanivannan.hireandseek.model.Employer;
import com.kaushikmanivannan.hireandseek.model.User;
import com.kaushikmanivannan.hireandseek.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final CandidateDAO candidateDAO;
    private final EmployerDAO employerDAO;

    @Autowired
    public UserServiceImpl(UserDAO theUserDAO, PasswordEncoder thePasswordEncoder, CandidateDAO theCandidateDAO, EmployerDAO theEmployerDAO) {
        userDAO = theUserDAO;
        passwordEncoder = thePasswordEncoder;
        candidateDAO = theCandidateDAO;
        employerDAO = theEmployerDAO;
    }

    @Override
    @Transactional
    public void save(RegistrationDTO registrationDTO) {
        User newUser = new User();
        newUser.setFirstName(registrationDTO.getFirstName());
        newUser.setLastName(registrationDTO.getLastName());
        newUser.setEmail(registrationDTO.getEmail());
        newUser.setPhone(registrationDTO.getPhone());
        newUser.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        newUser.setRole(UserRole.valueOf(registrationDTO.getRole()));
        newUser.setEnabled(true);
        // Save the user to the User table
        userDAO.save(newUser);

        // Depending on the role, also save to either Candidate or Employer table
        if (newUser.getRole() == UserRole.ROLE_CANDIDATE) {
            Candidate candidate = new Candidate();
            candidate.setUser(newUser);
            candidateDAO.save(candidate);
        } else if (newUser.getRole() == UserRole.ROLE_EMPLOYER) {
            Employer employer = new Employer();
            employer.setUser(newUser);
            employerDAO.save(employer);
        }
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findByPhone(String phone) {
        return userDAO.findByPhone(phone);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDAO.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
