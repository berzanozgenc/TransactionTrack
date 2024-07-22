package com.transactionTrack.ws.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransactionDto {

    @NotBlank(message = "Tutar Kısmı Boş Olamaz!")
    private BigDecimal amount;

    @NotBlank(message = "user_id")
    private Long user_id;

}
