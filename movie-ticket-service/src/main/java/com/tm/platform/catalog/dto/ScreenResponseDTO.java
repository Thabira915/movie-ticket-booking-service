package com.tm.platform.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponseDTO {
    private Long id;
    private String name;        // e.g., "Screen 1"
    private Integer capacity;   // Total seats in this screen

    private Long theaterId;
    private String theaterName;
    private String theaterCity;
}