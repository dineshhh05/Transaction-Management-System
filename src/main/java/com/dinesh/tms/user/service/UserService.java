package com.dinesh.tms.user.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.exception.DuplicateEmailException;
import com.dinesh.tms.user.exception.DuplicateUsernameException;
import com.dinesh.tms.user.exception.UnderageException;
import com.dinesh.tms.user.exception.UserNotFoundException;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.repository.UserRepository;



@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Transactional
    public User createUser(CreateUserRequest req){

        validateCreateUserParams(req);

        User newUser = new User(req.getUsername(), req.getEmail(), req.getFirstName(), req.getLastName(), req.getDateOfBirth(), req.getPostalCode());

        return userRepository.save(newUser);
    }


    public User getUserByID(UUID id){
        
        if (id == null) throw new IllegalArgumentException("ID cannot be null");

        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void deleteUserById(UUID id){

        if (id == null) throw new IllegalArgumentException("ID cannot be null");

        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException(id);
        }
    }


    // ---------- HELPER FUNCTIONS ----------

    private void validateCreateUserParams(CreateUserRequest req) {

        if(userRepository.existsByUsername(req.getUsername())){
            throw new DuplicateUsernameException(req.getUsername());
        }

        if(userRepository.existsByEmail(req.getEmail())){
            throw new DuplicateEmailException(req.getEmail());
        }

        int age = Period.between(req.getDateOfBirth(), LocalDate.now()).getYears();
        if(age < 18){
            throw new UnderageException();
        }
    }

}