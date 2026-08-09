package com.agrosoft.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class RegisterRequest {
    
    @NotBlank(message = "Email je obavezan!")
    @Email(message = "Email nije validan!")
    private String email;

    @NotBlank(message = "Username je obavezan!")
    private String username;

    @NotBlank(message = "Lozinka je obavezna!")
    @Size(min = 6, message = "Lozinka mora imati bar 6 karaktera!")
    private String password;

    @NotBlank(message = "Potvrda lozinke je obavezna!")
    private String confirmPassword;

    public RegisterRequest() { }

    public RegisterRequest(String email, String username, String password, String confirmPassword) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
