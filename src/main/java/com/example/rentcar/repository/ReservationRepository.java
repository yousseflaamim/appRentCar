package com.example.rentcar.repository;


import com.example.rentcar.modell.Renter;
import com.example.rentcar.modell.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRenter(Renter renter);
}
