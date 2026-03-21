package com.tm.platform.shows.inventory.dto;

import com.tm.platform.shows.inventory.model.SeatStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowSeatResponseDTO {
    private Long id;
    private String seatNumber;
    private SeatStatus status;
    private Double price;
}
