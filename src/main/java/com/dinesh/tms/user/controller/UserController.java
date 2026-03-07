package com.dinesh.tms.user.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.tms.account.dto.AccountResponse;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.account.service.AccountService;
import com.dinesh.tms.user.dto.CreateUserRequest;
import com.dinesh.tms.user.dto.UserResponse;
import com.dinesh.tms.user.model.User;
import com.dinesh.tms.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public UserController(UserService userService, AccountService accountService){
        this.userService = userService;
        this.accountService = accountService;
    }

    // ---------- POST METHODS ----------

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest req){
        
        User newUser = userService.createUser(req);

        UserResponse response = UserResponse.from(newUser);
       
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
    }


    // ---------- GET METHODS ----------

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserByID(@PathVariable("id") UUID id) {

        User fetchedUser = userService.getUserByID(id);

        UserResponse response = UserResponse.from(fetchedUser);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    // Explore Pagination for scalability
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        List<User> listOfUsers = userService.getAllUsers();

        List<UserResponse> listOfUserResponses = new ArrayList<>();

        for(User user : listOfUsers){
            listOfUserResponses.add(UserResponse.from(user)) ;
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(listOfUserResponses);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponse>> getUserAccounts(@PathVariable("userId") UUID userId){

        List<Account> listOfAccounts = accountService.getAccountsByUserID(userId);
        List<AccountResponse> listOfAccountResponse = new ArrayList<>();

        for(Account account : listOfAccounts ){
            listOfAccountResponse.add(AccountResponse.from(account));
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(listOfAccountResponse);
    }



    // ---------- DELETE METHODS ----------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id){

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}

