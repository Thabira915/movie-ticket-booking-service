package com.tm.platform.catalog.service;

import com.tm.platform.catalog.model.Movie;
import com.tm.platform.repository.mongodb.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    @Test
    void testSearchMovie_Success() {
        // Arrange: Prepare mock data and Pageable object
        Movie movie = new Movie();
        movie.setTitle("Inception");
        List<Movie> movieList = List.of(movie);

        // Match the logic in your searchMovie method (title, page, size)
        Pageable pageable = (Pageable) PageRequest.of(0, 5, Sort.by("title").ascending());
        when(movieRepository.findByTitleContainingIgnoreCase("Incep", (org.springframework.data.domain.Pageable) pageable))
                .thenReturn(movieList);

        // Act
        List<Movie> result = movieService.searchMovie("Incep", 0, 5);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Inception", result.get(0).getTitle());
    }

    @Test
    void testAddMoviesInBulk_Success() {
        // Arrange
        List<Movie> movies = List.of(new Movie(), new Movie());
        when(movieRepository.saveAll(movies)).thenReturn(movies);

        // Act
        List<Movie> result = movieService.addMoviesInBulk(movies);

        // Assert
        assertEquals(2, result.size());
        verify(movieRepository, times(1)).saveAll(movies);
    }

}