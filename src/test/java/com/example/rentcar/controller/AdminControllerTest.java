package com.example.rentcar.controller;





import com.example.rentcar.Service.AdminService;
import com.example.rentcar.modell.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLoginSuccess() throws Exception {
        // Given
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "password");

        when(adminService.login("admin", "password")).thenReturn("mocked-token");

        // When
        ResponseEntity<String> response = adminController.login(credentials);

        // Then
        verify(adminService, times(1)).login("admin", "password");
        assert response.getStatusCode() == HttpStatus.OK;
        assert response.getBody() != null && response.getBody().equals("mocked-token");
    }

    @Test
    void testLoginFailure() throws Exception {
        // Given
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "admin");
        credentials.put("password", "wrong-password");

        when(adminService.login("admin", "wrong-password")).thenThrow(new RuntimeException("Invalid credentials"));

        // When
        ResponseEntity<String> response = adminController.login(credentials);

        // Then
        verify(adminService, times(1)).login("admin", "wrong-password");
        assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;
        assert response.getBody() != null && response.getBody().equals("Invalid credentials");
    }

    @Test
    void testAddNewAdminSuccess() throws Exception {
        // Given
        Admin admin = new Admin();
        admin.setUsername("newadmin");
        admin.setPassword("password");

        when(adminService.addNewAdmin(any(Admin.class))).thenReturn(true);

        // When
        ResponseEntity<?> response = adminController.addNewAdmin(admin);

        // Then
        verify(adminService, times(1)).addNewAdmin(any(Admin.class));
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null && response.getBody().equals("Admin registered successfully");
    }

    @Test
    void testAddNewAdminFailure() throws Exception {
        // Given
        Admin admin = new Admin();
        admin.setUsername("existingadmin");
        admin.setPassword("password");

        when(adminService.addNewAdmin(any(Admin.class))).thenReturn(false);

        // When
        ResponseEntity<?> response = adminController.addNewAdmin(admin);

        // Then
        verify(adminService, times(1)).addNewAdmin(any(Admin.class));
        assert response.getStatusCode() == HttpStatus.BAD_REQUEST;
        assert response.getBody() != null && response.getBody().equals("Admin username already exists");
    }
}
