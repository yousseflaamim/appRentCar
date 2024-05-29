package com.example.rentcar.repository;


import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwnerId(Long ownerId);
Optional<Car> findByIdAndOwner(Long id, Owner owner);
Optional<Car> findByIdAndOwnerId(Long id, Long ownerId);
    }