package com.tm.platform.booking.controller;

import com.tm.platform.booking.dto.BookingRequestDTO;
import com.tm.platform.booking.dto.BookingResponseDTO;
import com.tm.platform.booking.model.Booking;
import com.tm.platform.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/book")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        Booking booking = bookingService.createBooking(requestDTO);
        return new ResponseEntity<>(convertToResponseDTO(booking), HttpStatus.CREATED);
    }

    //Helper method
    private BookingResponseDTO convertToResponseDTO(Booking booking) {
        BookingResponseDTO response = new BookingResponseDTO();

        // Mapping basic fields
        response.setBookingId(booking.getId());
        response.setUserId(booking.getUserId());
        response.setShowId(booking.getShowId());
        response.setTotalAmount(booking.getTotalAmount());
        response.setBookingTime(booking.getBookingTime());

        // Convert Enum to String for the API response
        if (booking.getStatus() != null) {
            response.setStatus(booking.getStatus().name());
        }

        // This is the "Transformation" part
        // Converting List<ShowSeat> (Entities) to List<String> (Seat Numbers)
        if (booking.getBookedSeats() != null) {
            List<String> seatNumbers = booking.getBookedSeats().stream()
                    .map(seat -> seat.getSeatNumber()) // Assumes getSeatNumber() exists in ShowSeat
                    .collect(Collectors.toList());
            response.setSeatNumbers(seatNumbers);
        }

        return response;
    }
}
