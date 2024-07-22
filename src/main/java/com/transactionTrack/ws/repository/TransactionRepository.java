package com.transactionTrack.ws.repository;

import com.transactionTrack.ws.model.Transaction;
import com.transactionTrack.ws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getAllByUser(User user);

    List<Transaction> findAllByUserAndTransactionDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);

    @Modifying
    @Query("DELETE FROM Transaction WHERE user.id = :userId")
    void deleteAllByUser(@Param("userId") long userId);

}
