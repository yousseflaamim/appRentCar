package com.example.rentcar.modell;

import jakarta.persistence.*;
import lombok.Data;


import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor

@Table(name = "renters")
public class Renter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Column(name = "renterBankAccountId")
    private String renterBankAccountId;
    //private String identityImage;
   //private String profileImage;

}