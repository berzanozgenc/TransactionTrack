package com.transactionTrack.ws.service;

import com.transactionTrack.ws.dto.PersonalTotalExpenseResponseDto;
import com.transactionTrack.ws.dto.TransactionDto;
import com.transactionTrack.ws.dto.TransactionResponseDto;
import com.transactionTrack.ws.dto.UpdateTransactionDto;
import com.transactionTrack.ws.exception.AmountValidationException;
import com.transactionTrack.ws.exception.TransactionNotFoundException;
import com.transactionTrack.ws.exception.UserNotFoundException;
import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.model.User;
import com.transactionTrack.ws.repository.TransactionRepository;
import com.transactionTrack.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    UserRepository userRepository;

    public TransactionResponseDto create(TransactionDto transactionDto, Long id){
        if (transactionDto.getAmount().compareTo(BigDecimal.ZERO) < 0){
            throw new AmountValidationException("Amount cannot be negative.");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setUser(user);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionResponseDto(
                savedTransaction.getTransactionDate(),
                savedTransaction.getAmount(),
                savedTransaction.getUser()
        );
    }

    public TransactionResponseDto getById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        return new TransactionResponseDto(
                transaction.getTransactionDate(),
                transaction.getAmount(),
                transaction.getUser()
        );
    }

    public TransactionResponseDto update(Long id, UpdateTransactionDto updateTransactionDto) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        User user = userRepository.findById(updateTransactionDto.getUser_id()).orElseThrow(() -> new UserNotFoundException("User not found"));
        transaction.setAmount(updateTransactionDto.getAmount());
        transaction.setUser(user);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return new TransactionResponseDto(
                updatedTransaction.getTransactionDate(),
                updatedTransaction.getAmount(),
                updatedTransaction.getUser()
        );
    }

    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        transactionRepository.delete(transaction);
    }

    public PersonalTotalExpenseResponseDto getTotalExpense(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Transaction> transactions = transactionRepository.getAllByUser(user);
        BigDecimal total = BigDecimal.ZERO;
        for (Transaction transaction: transactions){
            total = total.add(transaction.getAmount());
        }
        PersonalTotalExpenseResponseDto personalTotalExpenseResponseDto = new PersonalTotalExpenseResponseDto();
        personalTotalExpenseResponseDto.setTotalExpense(total);
        personalTotalExpenseResponseDto.setUser(user);
        return personalTotalExpenseResponseDto;
    }

}
