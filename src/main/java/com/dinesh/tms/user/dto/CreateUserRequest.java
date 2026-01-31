package com.dinesh.tms.user.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


public class CreateUserRequest {

    @NotBlank(message = "Username is required")
    private String username;
    
    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid Email")
    private String emailID; 

    @NotBlank
    private String firstName;  
    
    @NotBlank
    private String lastName;    

    @NotNull
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;  

    @NotBlank
    private String postalCode;


    // Getters and Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailID() {
        return emailID;
    }
    public void setEmailID(String emailID) {
        this.emailID = emailID;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

        
}
