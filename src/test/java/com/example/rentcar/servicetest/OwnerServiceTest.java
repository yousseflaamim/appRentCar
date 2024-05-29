package com.example.rentcar.servicetest;



import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.Service.OwnerService;
import com.example.rentcar.modell.Owner;
import com.example.rentcar.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private OwnerService ownerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterOwner() {
        Owner owner = new Owner();
        owner.setUsername("testuser");
        owner.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("token");
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        String token = ownerService.registerOwner(owner);

        assertEquals("token", token);
        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void testLoginSuccess() {
        Owner owner = new Owner();
        owner.setUsername("testuser");
        owner.setPassword("encodedPassword");

        when(ownerRepository.findByUsername(anyString())).thenReturn(owner);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtTokenUtil.generateToken(anyString())).thenReturn("token");

        String token = ownerService.login("testuser", "password");

        assertEquals("token", token);
    }

    @Test
    void testLoginFail_UserNotFound() {
        when(ownerRepository.findByUsername(anyString())).thenReturn(null);

        String result = ownerService.login("testuser", "password");

        assertEquals("User not found", result);
    }

    @Test
    void testLoginFail_IncorrectPassword() {
        Owner owner = new Owner();
        owner.setUsername("testuser");
        owner.setPassword("encodedPassword");

        when(ownerRepository.findByUsername(anyString())).thenReturn(owner);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        String result = ownerService.login("testuser", "password");

        assertEquals("Incorrect password", result);
    }
}
