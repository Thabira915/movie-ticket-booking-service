package com.tm.platform.catalog.service;

import com.tm.platform.catalog.dto.ScreenRequestDTO;
import com.tm.platform.catalog.dto.ScreenResponseDTO;
import com.tm.platform.catalog.model.Screen;
import com.tm.platform.catalog.model.Theater;
import com.tm.platform.repository.mysql.ScreenRepository;
import com.tm.platform.repository.mysql.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScreenService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;

    public ScreenResponseDTO addScreen(Long theaterId, ScreenRequestDTO screenRequestDTO) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found with id: " + theaterId));

        Screen savedScreen = new Screen();
        savedScreen.setScreenName(screenRequestDTO.getName());
        savedScreen.setTotalSeats(screenRequestDTO.getTotalCapacity());

        savedScreen.setTheater(theater);
        savedScreen = screenRepository.save(savedScreen);

        return mapToResponse(savedScreen);
    }

    private ScreenResponseDTO mapToResponse(Screen screen) {
        return ScreenResponseDTO.builder()
                .id(screen.getId())
                .name(screen.getScreenName())
                .capacity(screen.getTotalSeats())
                // Null-safe check to avoid NullPointerException if Theater is lazy-loaded
                .theaterId(screen.getTheater() != null ? screen.getTheater().getId() : null)
                .theaterName(screen.getTheater() != null ? screen.getTheater().getName() : "N/A")
                .theaterCity(screen.getTheater() != null ? screen.getTheater().getCity() : "N/A")
                .build();
    }

    public List<ScreenResponseDTO> getScreensByTheater(Long theaterId) {
        List<Screen> screens = screenRepository.findByTheaterId(theaterId);

        // Convert the list of entities to a list of DTOs
        return screens.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ScreenResponseDTO getScreenById(Long id) {
        Screen screen = screenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Screen not found with ID: " + id));

        return mapToResponse(screen);
    }
}
