package com.tm.platform.shows.service;

import com.tm.platform.catalog.model.Movie;
import com.tm.platform.repository.mongodb.MovieRepository;
import com.tm.platform.repository.mysql.ShowRepository;
import com.tm.platform.shows.dto.ShowRequestDTO;
import com.tm.platform.shows.dto.ShowResponseDTO;
import com.tm.platform.shows.inventory.service.ShowSeatService;
import com.tm.platform.shows.model.Show;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ShowSeatService showSeatService;

    @Transactional
    public ShowResponseDTO addShow(ShowRequestDTO showRequestDTO){
        // 1. Fetch Movie from Mongo to get duration
        Movie movie = movieRepository.findById(showRequestDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

         // 2. Calculate EndTime (Duration + 30 min cleaning buffer)
        LocalDateTime endTime = showRequestDTO.getStartTime().plusMinutes(movie.getDurationInMinutes() + 30);

        // 3. Check for Overlaps
        if (showRepository.existsOverlappingShow(showRequestDTO.getScreenId(), showRequestDTO.getStartTime(), endTime)) {
            throw new RuntimeException("Screen is already busy during this time slot!");
        }

        Show show = new Show();
        show.setMovieId(showRequestDTO.getMovieId());
        show.setPrice(showRequestDTO.getPrice());
        show.setStartTime(showRequestDTO.getStartTime());
        show.setTotalSeats(showRequestDTO.getTotalSeats());
        show.setAvailableSeats(showRequestDTO.getTotalSeats());
        show.setEndTime(endTime);

        show.setEndTime(showRequestDTO.getStartTime().plusHours(3));
        Show savedShow = showRepository.save(show);

        showSeatService.generateSeatsForShow(savedShow);
        return mapToResponse(savedShow);
    }

    private ShowResponseDTO mapToResponse(Show savedShow) {
        ShowResponseDTO showResponseDTO = new ShowResponseDTO();
        showResponseDTO.setShowId(savedShow.getId());
        showResponseDTO.setPrice(savedShow.getPrice());
        showResponseDTO.setStartTime(savedShow.getStartTime());
        showResponseDTO.setEndTime(savedShow.getEndTime());
        showResponseDTO.setAvailableSeats(savedShow.getAvailableSeats());
        showResponseDTO.setStatus(savedShow.getAvailableSeats() > 0 ? "Available" : "Sold Out");

        movieRepository.findById(savedShow.getMovieId()).ifPresent(movie -> {
            showResponseDTO.setMovieTitle(movie.getTitle());
            showResponseDTO.setMovieGenre(movie.getGenre());
        });

        if (savedShow.getScreen() != null && savedShow.getScreen().getTheater() != null) {
            showResponseDTO.setScreenName(savedShow.getScreen().getScreenName());
            showResponseDTO.setTheaterName(savedShow.getScreen().getTheater().getName());
            showResponseDTO.setTheaterCity(savedShow.getScreen().getTheater().getCity());
        }

        showResponseDTO.setStatus(savedShow.getAvailableSeats() > 0 ? "AVAILABLE" : "SOLD_OUT");

        return showResponseDTO;
    }

    public List<ShowResponseDTO> searchShowByCity(String city){
        List<Show> savedShow = showRepository.findByCity(city);
        return savedShow.stream().map(this::mapToResponse).toList();
    }

    public ShowResponseDTO searchShowById(Long id){
        Show savedShow = showRepository.findById(id).orElseThrow(() -> new RuntimeException("Show not found"));

        return mapToResponse(savedShow);
    }

    public List<ShowResponseDTO> screenSpecificShow(Long screenId){
        List<Show> savedShow = showRepository.findByScreen(screenId);

        return savedShow.stream().map(this::mapToResponse).toList();
    }

}
