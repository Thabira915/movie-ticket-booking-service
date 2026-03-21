package com.tm.platform.catalog.controller;

import com.tm.platform.catalog.dto.ScreenRequestDTO;
import com.tm.platform.catalog.dto.ScreenResponseDTO;
import com.tm.platform.catalog.service.ScreenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/screens")
@AllArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    // 1. Add a new screen to a specific theater
    @PostMapping("/theater/{theaterId}")
    public ScreenResponseDTO addScreen(@PathVariable Long theaterId,
                                       @RequestBody ScreenRequestDTO screenRequestDTO) {
        return screenService.addScreen(theaterId, screenRequestDTO);
    }

    // 2. Get all screens for a specific theater (for the dropdown in UI)
    @GetMapping("/theater/{theaterId}")
    public List<ScreenResponseDTO> getScreensByTheater(@PathVariable Long theaterId) {
        return screenService.getScreensByTheater(theaterId);
    }

    // 3. Get specific screen details
    @GetMapping("/{id}")
    public ScreenResponseDTO getScreenById(@PathVariable Long id) {
        return screenService.getScreenById(id);
    }
}