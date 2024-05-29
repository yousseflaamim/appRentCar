package com.example.rentcar.repository;



import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByUsername(String username);

}