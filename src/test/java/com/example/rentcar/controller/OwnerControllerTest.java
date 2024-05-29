package com.example.rentcar.controller;



import com.example.rentcar.Service.OwnerService;
import com.example.rentcar.modell.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterOwner() {
        Owner owner = new Owner();
        owner.setUsername("testuser");
        owner.setPassword("password");

        when(ownerService.registerOwner(any(Owner.class))).thenReturn("token");

        ResponseEntity<String> response = ownerController.registerOwner(owner);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }

    @Test
    void testRegisterOwnerException() {
        Owner owner = new Owner();
        owner.setUsername("testuser");
        owner.setPassword("password");

        when(ownerService.registerOwner(any(Owner.class))).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<String> response = ownerController.registerOwner(owner);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error registering owner: Database error", response.getBody());
    }

    @Test
    void testLoginSuccess() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "password");

        when(ownerService.login(anyString(), anyString())).thenReturn("token");

        ResponseEntity<Map<String, String>> response = ownerController.login(credentials);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().containsKey("token"));
        assertEquals("token", response.getBody().get("token"));
    }

    @Test
    void testLoginFail() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "testuser");
        credentials.put("password", "password");

        when(ownerService.login(anyString(), anyString())).thenReturn(null);

        ResponseEntity<Map<String, String>> response = ownerController.login(credentials);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("message"));
        assertEquals("Invalid credentials", response.getBody().get("message"));
    }
}
