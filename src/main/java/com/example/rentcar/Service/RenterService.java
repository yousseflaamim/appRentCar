package com.example.rentcar.Service;


import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.repository.CarRepository;
import com.example.rentcar.repository.RenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RenterService {

    private RenterRepository renterRepository;
    private CarRepository carRepository;
    private JwtTokenUtil jwtTokenUtil;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public RenterService(RenterRepository renterRepository,
                         CarRepository carRepository,
                         JwtTokenUtil jwtTokenUtil,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.renterRepository = renterRepository;
        this.carRepository = carRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    public String registerRenter(Renter renter) {

        renter.setPassword(passwordEncoder.encode(renter.getPassword()));
        renterRepository.save(renter);
        return jwtTokenUtil.generateToken(renter.getUsername());
    }

    public String login(String username, String password) {
        Renter renter = renterRepository.findByUsername(username);

        if (renter != null) {
            // Check if the provided password matches the password stored in the database
            boolean passwordMatch = passwordEncoder.matches(password, renter.getPassword());
            if (passwordMatch) {
                return jwtTokenUtil.generateToken(username); // Generate and return JWT token
            } else {
                return null; // Password doesn't match, return null
            }
        } else {
            return null; // Renter not found, return null
        }
    }


    public List<Car> getAllCars() {
        return carRepository.findAll();
    }


    public Renter findById(Long id) {
        return renterRepository.findById(id).orElse(null);
    }
}
