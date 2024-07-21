package com.transactionTrack.ws.repository;

import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getAllByUser(User user);

    List<Transaction> findAllByUserAndTransactionDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
}
