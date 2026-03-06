package com.dinesh.tms.user.dto;

import java.util.UUID;

import com.dinesh.tms.user.model.User;

public class UserResponse {
    private UUID id;
    private String username;
    private String emailID;
    private String firstName;
    private String lastName;

    public UserResponse(
        UUID id,
        String username,
        String emailID,
        String firstName,
        String lastName
    ) {
        this.id = id;
        this.username = username;
        this.emailID = emailID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserResponse from(User user){
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }


    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
}
