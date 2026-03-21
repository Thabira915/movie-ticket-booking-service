package com.tm.platform.catalog.controller;

import com.tm.platform.catalog.model.Theater;
import com.tm.platform.catalog.service.TheaterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theaters")
@AllArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    @PostMapping
    @Operation(summary = "Add a new theater", description = "Adds a new theater to the system")
    public Theater addTheater(@RequestBody Theater theater){
        return theaterService.addTheater(theater);
    }

    @PostMapping("/bulk")
    @Operation(summary = "Add theaters in bulk", description = "Adds multiple theaters to the system in a single request")
    public List<Theater> addTheaterInBulk(@RequestBody List<Theater> theaters){
        return theaterService.addTheaterInBulk(theaters);
    }

    @GetMapping
    @Operation(summary = "Get all theaters", description = "Retrieves a list of all theaters in the system")
    public List<Theater> getAllTheaters(){
        return theaterService.getAllTheaters(0, 10);
    }

    @GetMapping("/search")
    @Operation(summary = "Search theaters by city", description = "Searches for theaters located in a specific city")
    public List<Theater> searchTheaterByCity(@RequestParam String city){
        return theaterService.searchTheaterByCity(city, 0, 10);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Search theater by ID", description = "Searches for a theater by its unique ID")
    public Theater searchTheaterById(@PathVariable Long id){
       return theaterService.searchTheaterById(id);
    }
}
