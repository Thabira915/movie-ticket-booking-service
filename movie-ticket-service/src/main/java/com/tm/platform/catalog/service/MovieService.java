package com.tm.platform.catalog.service;

import com.tm.platform.catalog.model.Movie;
import com.tm.platform.repository.mongodb.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<Movie> getAllMovies(int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return movieRepository.findAllByGenre(genre, pageable);
    }

    public List<Movie> searchMovie(String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        return movieRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }

    public List<Movie> addMoviesInBulk(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }
}
