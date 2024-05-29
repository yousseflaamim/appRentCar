package com.example.rentcar.controller;




import com.example.rentcar.Service.RenterService;
import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RenterControllerTest {

    @Mock
    private RenterService renterService;

    @InjectMocks
    private RenterController renterController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterRenter() {
        Renter renter = new Renter();
        renter.setUsername("testuser");
        renter.setPassword("password");

        when(renterService.registerRenter(any(Renter.class))).thenReturn("token");

        ResponseEntity<String> response = renterController.registerRenter(renter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    void testRegisterRenterException() {
        Renter renter = new Renter();
        renter.setUsername("testuser");
        renter.setPassword("password");

        when(renterService.registerRenter(any(Renter.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = renterController.registerRenter(renter);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error registering owner: Database error", response.getBody());
    }

    @Test
    void testLoginSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "password");

        when(renterService.login(anyString(), anyString())).thenReturn("token");

        ResponseEntity<Map<String, String>> response = renterController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("token"));
        assertEquals("token", response.getBody().get("token"));
    }

    @Test
    void testLoginFail() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "password");

        when(renterService.login(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Map<String, String>> response = renterController.login(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("Invalid credentials", response.getBody().get("message"));
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        car1.setId(1L);
        Car car2 = new Car();
        car2.setId(2L);

        when(renterService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

        List<Car> cars = renterController.getAllCars();

        assertEquals(2, cars.size());
        assertEquals(1L, cars.get(0).getId());
        assertEquals(2L, cars.get(1).getId());
    }
}
