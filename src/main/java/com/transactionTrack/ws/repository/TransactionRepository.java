package com.transactionTrack.ws.repository;

import com.transactionTrack.ws.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
