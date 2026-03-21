package com.tm.platform.catalog.controller;

import com.tm.platform.catalog.model.Movie;
import com.tm.platform.catalog.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieve a list of all movies in the catalog")
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies(0, 10);
    }

    @GetMapping("/genre")
    @Operation(summary = "Get movies by genre", description = "Retrieve a list of movies that belong to a specific genre")
    public List<Movie> getMoviesByGenre(@RequestParam String genre){
        return movieService.getMoviesByGenre(genre, 0, 10);
    }

    @GetMapping("/search")
    @Operation(summary = "Search for a movie by title", description = "Retrieve a movie from the catalog by its title")
    public List<Movie> searchMovie(@RequestParam String title){
        return movieService.searchMovie(title, 0, 10);
    }

    @PostMapping
    @Operation(summary = "Add a new movie", description = "Add a new movie to the catalog")
    public Movie addMovie(@RequestBody Movie movie){
       return movieService.addMovie(movie);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add movies in bulk", description = "Add multiple movies to the catalog in a single request")
    public List<Movie> addMoviesInBulk(@RequestBody List<Movie> movies){
        return movieService.addMoviesInBulk(movies);
    }
}
