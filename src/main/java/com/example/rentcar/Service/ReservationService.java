package com.example.rentcar.Service;

import com.example.rentcar.modell.Car;
import com.example.rentcar.modell.Renter;
import com.example.rentcar.modell.Reservation;
import com.example.rentcar.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getRenterReservations(Renter renter) {
        return reservationRepository.findByRenter(renter);
    }

    public Reservation makeReservation(Car car, Renter renter, LocalDate startDate, LocalDate endDate, String bankAccountId) {
        Reservation reservation = new Reservation();
        reservation.setCar(car);
        reservation.setRenter(renter);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setBankAccountId(bankAccountId);
        return reservationRepository.save(reservation);
    }

    public void cancelReservation(Long reservationId, Renter renter) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        optionalReservation.ifPresent(reservation -> {
            if (reservation.getRenter().equals(renter)) {
                reservationRepository.delete(reservation);
            }
        });
    }


    public Reservation updateReservation(Long reservationId, LocalDate startDate, LocalDate endDate, Renter renter) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        return optionalReservation.map(reservation -> {
            if (reservation.getRenter().equals(renter)) {
                reservation.setStartDate(startDate);
                reservation.setEndDate(endDate);
                return reservationRepository.save(reservation);
            }
            return null;
        }).orElse(null);
    }


}
