package com.example.rentcar.servicetest;

import com.example.rentcar.Service.ReservationService;

import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.modell.Reservation;
import com.example.rentcar.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRenterReservations() {
        Renter renter = new Renter();
        List<Reservation> expectedReservations = new ArrayList<>();
        when(reservationRepository.findByRenter(renter)).thenReturn(expectedReservations);

        List<Reservation> actualReservations = reservationService.getRenterReservations(renter);

        assertEquals(expectedReservations, actualReservations);
    }

    @Test
    void testMakeReservation() {
        Car car = new Car();
        Renter renter = new Renter();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(5);
        String bankAccountId = "123456";

        Reservation savedReservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(savedReservation);

        Reservation result = reservationService.makeReservation(car, renter, startDate, endDate, bankAccountId);

        assertEquals(savedReservation, result);
    }
    @Test
    void testUpdateReservation() {
        // Prepare test data
        Long reservationId = 1L;
        LocalDate startDate = LocalDate.of(2024, 2, 19);
        LocalDate endDate = LocalDate.of(2024, 2, 21);
        Renter renter = new Renter();
        Reservation existingReservation = new Reservation();
        existingReservation.setRenter(renter);

        // Mock behavior of repository
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existingReservation));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        Reservation updatedReservation = reservationService.updateReservation(reservationId, startDate, endDate, renter);

        // Assert the result
        assertEquals(startDate, updatedReservation.getStartDate());
        assertEquals(endDate, updatedReservation.getEndDate());
    }

    // Similar tests for cancelReservation and updateReservation methods...
}
