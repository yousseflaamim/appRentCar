package com.example.rentcar.controller;

import com.example.rentcar.Service.RenterService;
import com.example.rentcar.Service.ReservationService;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.modell.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    private final RenterService renterService;

    @Autowired
    public ReservationController(ReservationService reservationService, RenterService renterService) {
        this.reservationService = reservationService;
        this.renterService = renterService;
    }
    @PostMapping("/makeReservation")
    public ResponseEntity<Reservation> makeReservation(@RequestBody Reservation reservation) {
        // Assuming the request body contains all necessary reservation information
        Reservation savedReservation = reservationService.makeReservation(
                reservation.getCar(), reservation.getRenter(),
                reservation.getStartDate(), reservation.getEndDate(),
                reservation.getBankAccountId());

        return new ResponseEntity<>(savedReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public void cancelReservation(@PathVariable Long reservationId, @RequestBody Renter renter) {
        reservationService.cancelReservation(reservationId, renter);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Long reservationId,
            @RequestBody Map<String, Object> updates) {

        LocalDate startDate = LocalDate.parse((String) updates.get("startDate"));
        LocalDate endDate = LocalDate.parse((String) updates.get("endDate"));


        Map<String, Object> renterMap = (Map<String, Object>) updates.get("renter");
        Long renterId = Long.valueOf((Integer) renterMap.get("id"));
        Renter renter = renterService.findById(renterId);

        if (renter == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Reservation updated = reservationService.updateReservation(reservationId, startDate, endDate, renter);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}
