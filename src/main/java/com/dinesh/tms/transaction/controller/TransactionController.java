package com.dinesh.tms.transaction.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dinesh.tms.transaction.model.Transaction;
import com.dinesh.tms.transaction.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController (TransactionService transactionService){
        this.transactionService = transactionService;
    }

    // @PostMapping
    // public Transaction createTransaction(@RequestBody TransactionRequest request){
    //     return transactionService.addTransaction(request.senderId, request.receiverId, request.amount, request.description);
    // }

    // public static class TransactionRequest {
    //     private UUID senderId;
    //     private UUID receiverId;
    //     private BigDecimal amount;
    //     private String description;

    //     // getters and setters
    //     public UUID getSenderId() { return senderId; }
    //     public void setSenderId(UUID senderId) { this.senderId = senderId; }
    //     public UUID getReceiverId() { return receiverId; }
    //     public void setReceiverId(UUID receiverId) { this.receiverId = receiverId; }
    //     public BigDecimal getAmount() { return amount; }
    //     public void setAmount(BigDecimal amount) { this.amount = amount; }
    //     public String getDescription() { return description; }
    //     public void setDescription(String description) { this.description = description; }
    // }
}
