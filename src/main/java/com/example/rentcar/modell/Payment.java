package com.example.rentcar.modell;

import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_bank_account_id")
    private String ownerBankAccountId;

    @Column(name = "renter_bank_account_id")
    private String renterBankAccountId;

    private BigDecimal amount;
    private LocalDateTime paymentDate;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

}
