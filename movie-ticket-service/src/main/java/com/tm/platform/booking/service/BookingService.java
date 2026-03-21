package com.tm.platform.booking.service;

import com.tm.platform.booking.dto.BookingRequestDTO;
import com.tm.platform.booking.model.Booking;
import com.tm.platform.booking.model.BookingStatus;
import com.tm.platform.repository.mysql.BookingRepository;
import com.tm.platform.repository.mysql.ShowSeatRepository;
import com.tm.platform.shows.inventory.model.SeatStatus;
import com.tm.platform.shows.inventory.model.ShowSeat;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public Booking createBooking(BookingRequestDTO bookingRequestDTO){
        // 1. Log the Entry Point
        log.info("START: Processing booking request for Show ID: {} | User ID: {} | Seats: {}",
                bookingRequestDTO.getShowId(), bookingRequestDTO.getUserId(), bookingRequestDTO.getSeatIds());

        // 2. Log the Concurrency Attempt (Crucial for HLD documentation)
        log.debug("Attempting to acquire database lock for {} seats...", bookingRequestDTO.getSeatIds().size());

        List<ShowSeat> availableSeats = showSeatRepository.findAvailableSeatsWithLock(bookingRequestDTO.getSeatIds());

        // 3. Log the Validation Outcome (Failure Path)
        if (availableSeats.size() != bookingRequestDTO.getSeatIds().size()) {
            log.warn("BOOKING FAILED: Concurrency conflict or invalid seats. Expected {}, but found {} available.",
                    bookingRequestDTO.getSeatIds().size(), availableSeats.size());
            throw new RuntimeException("One or more seats are no longer available!");
        }

        // 4. Log the State Transition
        log.debug("Lock acquired. Updating seat status to BOOKED for seats: {}", bookingRequestDTO.getSeatIds());
        availableSeats.forEach(seat -> seat.setStatus(SeatStatus.BOOKED));
        showSeatRepository.saveAll(availableSeats);

        // 5. Log the Final Persistence
        Booking booking = new Booking();
        booking.setShowId(bookingRequestDTO.getShowId());
        booking.setUserId(bookingRequestDTO.getUserId());
        booking.setBookedSeats(availableSeats);
        booking.setTotalAmount(calculateTotal(availableSeats));
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        log.info("SUCCESS: Booking created with ID: {} for Amount: {}", savedBooking.getId(), savedBooking.getTotalAmount());

        return savedBooking;
    }

    private Double calculateTotal(List<ShowSeat> seats) {
        Double total = seats.stream().mapToDouble(ShowSeat::getPrice).sum();
        log.debug("Calculated total amount: {}", total);
        return total;
    }
}
