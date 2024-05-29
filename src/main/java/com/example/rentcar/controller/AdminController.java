package com.example.rentcar.controller;

import com.example.rentcar.Service.AdminService;
import com.example.rentcar.modell.Admin;
import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.modell.Renter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        String token = adminService.login(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


    @PostMapping("/addNewAdmin")
    public ResponseEntity<?> addNewAdmin(@RequestBody Admin admin) {
        boolean success = adminService.addNewAdmin(admin);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin username already exists");
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = adminService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/owners")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> owners = adminService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/renters")
    public ResponseEntity<List<Renter>> getAllRenters() {
        List<Renter> renters = adminService.getAllRenters();
        return ResponseEntity.ok(renters);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCarById(@PathVariable Long id, @RequestBody Car car) {
        Car updatedCar = adminService.updateCarById(id, car);
        return ResponseEntity.ok(updatedCar);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<Owner> updateOwnerById(@PathVariable Long id, @RequestBody Owner owner) {
        Owner updatedOwner = adminService.updateOwnerById(id, owner);
        return ResponseEntity.ok(updatedOwner);
    }

    @PutMapping("/renters/{id}")
    public ResponseEntity<Renter> updateRenterById(@PathVariable Long id, @RequestBody Renter renter) {
        Renter updatedRenter = adminService.updateRenterById(id, renter);
        return ResponseEntity.ok(updatedRenter);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdminById(@PathVariable Long id) {
        boolean success = adminService.deleteAdminById(id);
        if (success) {
            return ResponseEntity.ok("Admin deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not found");
        }
    }
}
