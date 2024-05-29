package com.example.rentcar.Service;


import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    // Add a new car for the authenticated owner
    public Car addCar(Car car, Owner owner) {
        car.setOwner(owner);
        return carRepository.save(car);
    }

    // Delete a car if it belongs to the authenticated owner
    public void deleteCar(Long carId, Owner owner) {
        // Retrieve the car from the database
        Optional<Car> optionalCar = carRepository.findById(carId);
        optionalCar.ifPresent(car -> {
            // Check if the car belongs to the authenticated owner
            if (car.getOwner().equals(owner)) {
                carRepository.delete(car);
            }
        });
    }

    // Update car information if it belongs to the authenticated owner
    public Car updateCar(Car updatedCar, Owner owner) {
        // Retrieve the car from the database
        Optional<Car> optionalCar = carRepository.findById(updatedCar.getId());
        return optionalCar.map(car -> {
            // Check if the car belongs to the authenticated owner
            if (car.getOwner().equals(owner)) {
                // Update car information
                car.setCarType(updatedCar.getCarType());
                car.setRentPrice(updatedCar.getRentPrice());
                car.setLocation(updatedCar.getLocation());
                // Save the updated car
                return carRepository.save(car);
            }
            return null; // Handle case where the car does not belong to the authenticated owner
        }).orElse(null); // Handle case where the car is not found
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void getCarById(Long carId) {
    }


    // Method to extract owner ID from the JWT token



}