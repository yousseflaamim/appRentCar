package com.example.rentcar.Service;

import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.modell.Admin;
import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.repository.AdminRepository;
import com.example.rentcar.repository.CarRepository;
import com.example.rentcar.repository.OwnerRepository;
import com.example.rentcar.repository.RenterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;



    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    public String login(String username, String password) {
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null) {
            boolean passwordMatch = passwordEncoder.matches(password, admin.getPassword());

            if (passwordMatch) {
                return jwtTokenUtil.generateToken(username);
            }
        }

        throw new UsernameNotFoundException("Invalid username or password");
    }
    public boolean registerAdmin(String username, String password) {
        if (adminRepository.existsByUsername(username)) {
            return false;
        }

        Admin admin = new Admin();
        admin.setUsername(username);

        String encodedPassword = passwordEncoder.encode(password);
        admin.setPassword(encodedPassword);

        adminRepository.save(admin);

        return true;
    }


    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public List<Renter> getAllRenters() {
        return renterRepository.findAll();
    }

    public Car updateCarById(Long id, Car car) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Car not found"));

        existingCar.setCarType(car.getCarType());
        existingCar.setLocation(car.getLocation());
        existingCar.setRentPrice(car.getRentPrice());

        return carRepository.save(existingCar);
    }

    public Owner updateOwnerById(Long id, Owner owner) {
        Owner existingOwner = ownerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found"));

        existingOwner.setUsername(owner.getUsername());
        existingOwner.setPassword(owner.getPassword());
        existingOwner.setOwnerBankAccountId(owner.getOwnerBankAccountId());

        return ownerRepository.save(existingOwner);
    }

    public Renter updateRenterById(Long id, Renter renter) {
        Renter existingRenter = renterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Renter not found"));

        existingRenter.setUsername(renter.getUsername());
        existingRenter.setPassword(renter.getPassword());
        existingRenter.setRenterBankAccountId(renter.getRenterBankAccountId());


        return renterRepository.save(existingRenter);
    }

    public boolean addNewAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            return false;
        }
        String encodedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodedPassword);
        adminRepository.save(admin);
        return true;
    }

    public boolean deleteAdminById(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
