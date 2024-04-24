package com.kaushikmanivannan.hireandseek.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

public class RegistrationDTO {

    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First Name cannot contain more than 50 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(max = 50, message = "First Name cannot contain more than 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Please enter a valid phone number")
    private String phone;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be at least 8 characters long and include at least one lowercase letter, one uppercase letter, one number, and one special character")
    private String password;

    @NotNull(message = "Role is required")
    private String role;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String firstName, String lastName, String email, String phone, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
