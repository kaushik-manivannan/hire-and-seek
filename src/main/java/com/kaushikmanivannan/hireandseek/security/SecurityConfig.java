package com.kaushikmanivannan.hireandseek.security;

import com.kaushikmanivannan.hireandseek.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource, PasswordEncoder passwordEncoder){
//        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
//        userDetailsManager.setUsersByUsernameQuery(
//                "SELECT email, password, enabled FROM user WHERE email=?"
//        );
//        userDetailsManager.setAuthoritiesByUsernameQuery(
//                "SELECT email, role FROM user WHERE email=?"
//        );
//        return userDetailsManager;
//    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/processRegistrationForm").permitAll()
                                .requestMatchers("/home").hasAnyRole("CANDIDATE", "EMPLOYER")
                                .requestMatchers("/images/**").permitAll()
                                .requestMatchers("/css/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/")
                                .loginProcessingUrl("/processLoginForm")
                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                )
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedPage("/access-denied"))
                .sessionManagement(session -> session
                        .sessionFixation().migrateSession()
                );

        return http.build();
    }
}
