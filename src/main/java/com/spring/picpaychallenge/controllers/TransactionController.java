package com.spring.picpaychallenge.controllers;

import com.spring.picpaychallenge.dto.TransactionDTO;
import com.spring.picpaychallenge.entities.Transaction;
import com.spring.picpaychallenge.entities.User;
import com.spring.picpaychallenge.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> performTransaction(User sender, User receiver, BigDecimal amount) throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(amount, sender.getId(), receiver.getId());
        Transaction transaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
