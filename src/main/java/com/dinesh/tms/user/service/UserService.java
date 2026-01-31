package com.dinesh.tms.user.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(CreateUserRequest req){

        validateCreateUserParams(req);

        User newUser = new User(req.getUsername(), req.getEmailID(), req.getFirstName(), req.getLastName(), req.getDateOfBirth(), req.getPostalCode());

        return userRepository.save(newUser);
    }


    // TODO: implement custom exceptions
    public void validateCreateUserParams(CreateUserRequest req) {

        if(userRepository.existsByUsername(req.getUsername())){
            throw new IllegalArgumentException("Username already exists.");
        }

        int age = Period.between(req.getDateOfBirth(), LocalDate.now()).getYears();
        if(age <= 18){
            throw new IllegalArgumentException("Age must be greater than 18 years.");
        }
    }

}