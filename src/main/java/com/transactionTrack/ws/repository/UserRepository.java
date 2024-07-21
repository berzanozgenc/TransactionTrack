package com.transactionTrack.ws.repository;

import com.transactionTrack.ws.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
