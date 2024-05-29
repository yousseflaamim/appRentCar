package com.example.rentcar.modell;

import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "renter_id")
    private Renter renter;

    private LocalDate startDate;
    private LocalDate endDate;
    private String bankAccountId;


}
