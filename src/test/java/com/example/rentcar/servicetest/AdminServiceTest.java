package com.example.rentcar.servicetest;


import com.example.rentcar.JWT.JwtTokenUtil;
import com.example.rentcar.Service.AdminService;
import com.example.rentcar.modell.Admin;
import com.example.rentcar.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AdminService adminService;

    @Test
    void testLoginSuccess() {
        // Given
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("$2a$10$OQ.wGLtjBvZtaJAOH1/0yOC2pcozBY4b0u0SLE7jUofUPc/kMxm5G"); // Encoded password

        when(adminRepository.findByUsername("admin")).thenReturn(admin);
        when(passwordEncoder.matches(any(CharSequence.class), any(String.class))).thenReturn(true);
        when(jwtTokenUtil.generateToken("admin")).thenReturn("mocked-token");

        // When
        String token = adminService.login("admin", "password");

        // Then
        assertNotNull(token);
        assertEquals("mocked-token", token);
        verify(adminRepository, times(1)).findByUsername("admin");
    }

    @Test
    void testLoginFailure() {
        // Given
        when(adminRepository.findByUsername("admin")).thenReturn(null);

        // When / Then
        assertThrows(UsernameNotFoundException.class, () -> adminService.login("admin", "password"));
    }

}
