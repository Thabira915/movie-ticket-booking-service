package com.tm.platform.catalog.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScreenRequestDTO {

    @NotBlank(message = "Screen name is mandatory")
    private String name; // e.g., "Screen 1", "Audi 2", "IMAX"

    @NotNull(message = "Total capacity is mandatory")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer totalCapacity;

    @NotNull(message = "Theater ID is mandatory")
    private Long theaterId; // Used to link the screen to its parent theater
}