package com.transactionTrack.ws.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name="TransactionTrack_User")
@Entity
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

}
