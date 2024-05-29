package com.example.rentcar.modell;

import jakarta.persistence.*;
import lombok.Data;

import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@Table(name = "admins")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;


}
