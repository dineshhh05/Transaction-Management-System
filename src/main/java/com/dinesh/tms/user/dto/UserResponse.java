package com.dinesh.tms.user.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.dinesh.tms.account.dto.AccountResponse;
import com.dinesh.tms.account.model.Account;
import com.dinesh.tms.user.model.User;

// Response DTO, Read only
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<AccountResponse> accounts; 

    public UserResponse(
        UUID id,
        String username,
        String email,
        String firstName,
        String lastName,
        List<AccountResponse> accounts
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.accounts = accounts;
    }

    // DTO mapper
    public static UserResponse from(User user){

        List<Account> listOfAccounts = user.getAccounts();

        List<AccountResponse> listOfAccountResponse = new ArrayList<>();

        for(Account account : listOfAccounts){
            listOfAccountResponse.add(AccountResponse.from(account));
        }

        return new UserResponse(
            user.getId(), 
            user.getUsername(), 
            user.getEmail(), 
            user.getFirstName(), 
            user.getLastName(), 
            listOfAccountResponse
        );
    }


    // Getters only as its Read only
    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<AccountResponse> getAccounts() {
        return accounts;
    }

}
