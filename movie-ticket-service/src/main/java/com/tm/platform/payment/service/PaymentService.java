package com.tm.platform.payment.service;

import com.tm.platform.booking.model.Booking;
import com.tm.platform.booking.model.BookingStatus;
import com.tm.platform.payment.dto.PaymentRequestDTO;
import com.tm.platform.repository.mysql.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentService {

    private final BookingRepository bookingRepository;

    @Transactional
    public String processPayment(PaymentRequestDTO paymentRequestDTO){
        Booking booking = bookingRepository.findById(paymentRequestDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if(!booking.getTotalAmount().equals(paymentRequestDTO.getAmount())){
            throw new RuntimeException("Invalid payment amount");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        return "Payment successful for booking ID: " + booking.getId();
    }

}
