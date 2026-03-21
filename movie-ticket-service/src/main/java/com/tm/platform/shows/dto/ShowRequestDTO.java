package com.tm.platform.shows.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShowRequestDTO {
    @NotBlank(message = "Movie ID is required")
    private String movieId; // The MongoDB ID

    @NotNull(message = "Screen ID is required")
    private Long screenId; // The MySQL ID

    @NotNull(message = "Start time is required")
    @Future(message = "Show must be in the future")
    private LocalDateTime startTime;

    private Double price;
    private Integer totalSeats;
}
