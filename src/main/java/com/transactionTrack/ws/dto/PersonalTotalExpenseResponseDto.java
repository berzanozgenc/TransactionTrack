package com.transactionTrack.ws.dto;

import com.transactionTrack.ws.model.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class PersonalTotalExpenseResponseDto {

    private BigDecimal totalExpense;
    private User user;

}
