package com.tm.platform.shows.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponseDTO {

    private Long showId;

    // Data from MongoDB (fetched via movieId)
    private String movieTitle;
    private String movieGenre;
    private String posterUrl;

    // Data from MySQL (fetched via Screen/Theater relationship)
    private String theaterName;
    private String theaterCity;
    private String screenName;

    // Show Specifics
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
    private Integer availableSeats;
    private String status; // e.g., "AVAILABLE", "ALMOST_FULL", "SOLD_OUT"
}
