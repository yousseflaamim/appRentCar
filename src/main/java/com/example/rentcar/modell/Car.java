package com.example.rentcar.modell;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "longblob")
    private  String image;
    private String carType;
    private BigDecimal rentPrice;
    private String location;
    private boolean availableForBooking;
    private String model;
    private String color;
    private Integer seatingCapacity;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

}
