package com.example.rentcar.controller;

import com.example.rentcar.Service.CarService;
import com.example.rentcar.modell.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car, @RequestParam Long ownerId) {
        try {
            Car savedCar = carService.addCar(car, ownerId);
            return ResponseEntity.ok(savedCar);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    @GetMapping("/{id}/owner/{ownerId}")
    public ResponseEntity<Car> getCarByIdAndOwner(@PathVariable Long id, @PathVariable Long ownerId) {
        return carService.getCarByIdAndOwner(id, ownerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
}
