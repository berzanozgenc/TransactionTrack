package com.transactionTrack.ws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table
@Schema
@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the transaction", example = "1")
    private Long id;

    @Column(nullable = false)
    @Schema(description = " Amount of the transaction", example = "250")
    private BigDecimal amount;

    @Column(nullable = false)
    @Schema(description = "Date of the transaction")
    private LocalDateTime transactionDate;

    @ManyToOne
    @Schema(description = "User of the transaction")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
