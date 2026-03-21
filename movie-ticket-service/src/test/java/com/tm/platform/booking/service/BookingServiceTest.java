package com.tm.platform.booking.service;

import com.tm.platform.booking.model.Booking;
import com.tm.platform.repository.mysql.BookingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void testGetBookingById_Success(){
        Booking mockBooking = new Booking();
        mockBooking.setId(2L);
        when(bookingRepository.findById(2L)).thenReturn(Optional.of(mockBooking));

        Booking result = bookingService.getBookingById(2L);

        assertNotNull(result);
        assertEquals(2L, result.getId());
    }
}