package com.tm.platform.shows.inventory.controller;

import com.tm.platform.shows.inventory.dto.ShowSeatResponseDTO;
import com.tm.platform.shows.inventory.service.ShowSeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/show-seats")
@RequiredArgsConstructor
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<ShowSeatResponseDTO>> getSeatsByShow(@PathVariable Long showId) {
        List<ShowSeatResponseDTO> response = showSeatService.getSeatsByShow(showId)
                .stream()
                .map(seat -> ShowSeatResponseDTO.builder()
                        .id(seat.getId())
                        .seatNumber(seat.getSeatNumber())
                        .status(seat.getStatus())
                        .price(seat.getPrice())
                        .build())
                .toList();

        return ResponseEntity.ok(response);
    }
}
