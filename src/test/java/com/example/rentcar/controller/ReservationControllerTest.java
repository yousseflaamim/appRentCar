package com.example.rentcar.controller;






import com.example.rentcar.Service.ReservationService;

import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.modell.Reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ReservationController reservationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
    }

    @Test
    void testMakeReservation() throws Exception {
        // Create sample reservation data
        Car car = new Car();
        Renter renter = new Renter();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        String bankAccountId = "123456";

        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setRenter(renter);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setBankAccountId(bankAccountId);

        Reservation savedReservation = new Reservation(); // Create sample saved reservation
        when(reservationService.makeReservation(any(), any(), any(), any(), any())).thenReturn(savedReservation);

        // Perform POST request to the endpoint
        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"car\":{},\"renter\":{},\"startDate\":\"2024-02-19\",\"endDate\":\"2024-02-24\",\"bankAccountId\":\"123456\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testCancelReservation() throws Exception {
        // Perform DELETE request to the endpoint
        mockMvc.perform(delete("/api/reservations/{reservationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"renter\":{}}"))
                .andExpect(status().isOk());

        // Verify that the service method is called with the correct arguments
        verify(reservationService).cancelReservation(eq(1L), any(Renter.class));
    }



}