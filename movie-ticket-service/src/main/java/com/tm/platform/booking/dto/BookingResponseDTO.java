package com.tm.platform.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long bookingId;
    private String userId;
    private Long showId;
    private List<String> seatNumbers; // This matches your stream map
    private Double totalAmount;
    private String status;
    private LocalDateTime bookingTime;
}
