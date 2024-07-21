package com.transactionTrack.ws.dto;

import com.transactionTrack.ws.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private LocalDateTime transactionDate;
    private BigDecimal amount;
    private User user;

}
