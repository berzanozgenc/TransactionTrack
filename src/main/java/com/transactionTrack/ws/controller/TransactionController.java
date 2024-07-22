package com.transactionTrack.ws.controller;

import com.transactionTrack.ws.configuration.CurrentUser;
import com.transactionTrack.ws.dto.PersonalTotalExpenseResponseDto;
import com.transactionTrack.ws.dto.TransactionDto;
import com.transactionTrack.ws.dto.TransactionResponseDto;
import com.transactionTrack.ws.dto.UpdateTransactionDto;
import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionDto transactionDto, Authentication authentication) {
        Long userId = ((CurrentUser)authentication.getPrincipal()).getId();
        TransactionResponseDto createdTransaction = transactionService.create(transactionDto, userId);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long id) {
        TransactionResponseDto transaction = transactionService.getById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> updateTransaction(@PathVariable Long id, @RequestBody UpdateTransactionDto updateTransactionDto) {
        TransactionResponseDto updatedTransaction = transactionService.update(id, updateTransactionDto);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/personalTotalExpense")
    public ResponseEntity<PersonalTotalExpenseResponseDto> getTotalExpenseByUser(Authentication authentication) {
        Long userId = ((CurrentUser)authentication.getPrincipal()).getId();
        PersonalTotalExpenseResponseDto totalExpense = transactionService.getTotalExpense(userId);
        return new ResponseEntity<>(totalExpense, HttpStatus.OK);
    }

}
