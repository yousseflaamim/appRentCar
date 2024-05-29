package com.example.rentcar.servicetest;



import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.Service.RenterService;
import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.repository.CarRepository;
import com.example.rentcar.repository.RenterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RenterServiceTest {

    @Mock
    private RenterRepository renterRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private RenterService renterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterRenter() {
        Renter renter = new Renter();
        renter.setUsername("testuser");
        renter.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("token");
        when(renterRepository.save(any(Renter.class))).thenReturn(renter);

        String token = renterService.registerRenter(renter);

        assertEquals("token", token);
        verify(renterRepository, times(1)).save(any(Renter.class));
    }

    @Test
    void testLoginSuccess() {
        Renter renter = new Renter();
        renter.setUsername("testuser");
        renter.setPassword("encodedPassword");

        when(renterRepository.findByUsername(anyString())).thenReturn(renter);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("token");

        String token = renterService.login("testuser", "password");

        assertEquals("token", token);
    }

    @Test
    void testLoginFail_UserNotFound() {
        when(renterRepository.findByUsername(anyString())).thenReturn(null);

        String result = renterService.login("testuser", "password");

        assertEquals("User not found", result);
    }

    @Test
    void testLoginFail_IncorrectPassword() {
        Renter renter = new Renter();
        renter.setUsername("testuser");
        renter.setPassword("encodedPassword");

        when(renterRepository.findByUsername(anyString())).thenReturn(renter);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        String result = renterService.login("testuser", "password");

        assertEquals("Incorrect password", result);
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(2L);

        when(carRepository.findAll()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = renterService.getAllCars();

        assertEquals(2, cars.size());
        assertEquals(1L, cars.get(0).getId());
        assertEquals(2L, cars.get(1).getId());
    }
}
