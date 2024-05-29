package com.example.rentcar.repository;



import com.example.rentcar.modell.Owner;
import com.example.rentcar.modell.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<Renter, Long> {
    boolean existsByUsernameAndPassword(String username, String password);

    Renter findByUsername(String username);


}