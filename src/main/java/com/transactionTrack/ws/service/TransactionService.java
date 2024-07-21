package com.transactionTrack.ws.service;

import com.transactionTrack.ws.dto.TransactionDto;
import com.transactionTrack.ws.dto.TransactionResponseDto;
import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public TransactionResponseDto create(TransactionDto transactionDto){
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(transactionDto.getAmount());
        //EKLEME YAP
        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionResponseDto(
                savedTransaction.getTransactionDate(),
                savedTransaction.getAmount(),
                savedTransaction.getUser()
        );
    }

    public TransactionResponseDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        return new TransactionResponseDto(
                transaction.getTransactionDate(),
                transaction.getAmount(),
                transaction.getUser()
        );
    }

    public TransactionResponseDto update(Long id, TransactionDto transactionDto) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transaction.setAmount(transactionDto.getAmount());
        //EKLEME YAP
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return new TransactionResponseDto(
                updatedTransaction.getTransactionDate(),
                updatedTransaction.getAmount(),
                updatedTransaction.getUser()
        );
    }

    @Transactional
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
        transactionRepository.delete(transaction);
    }
}
