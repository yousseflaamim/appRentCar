package com.example.rentcar.Service;

import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.repository.CarRepository;
import com.example.rentcar.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    public Car addCar(Car car, Long ownerId) {
        Optional<Owner> ownerOptional = ownerRepository.findById(ownerId);
        if (ownerOptional.isPresent()) {
            car.setOwner(ownerOptional.get());
            return carRepository.save(car);
        } else {
            throw new RuntimeException("Owner not found with ID: " + ownerId);
        }
    }

    public Optional<Car> getCarByIdAndOwner(Long id, Long ownerId) {
        return carRepository.findByIdAndOwnerId(id, ownerId);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
