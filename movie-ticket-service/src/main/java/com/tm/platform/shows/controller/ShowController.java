package com.tm.platform.shows.controller;

import com.tm.platform.shows.dto.ShowRequestDTO;
import com.tm.platform.shows.dto.ShowResponseDTO;
import com.tm.platform.shows.service.ShowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
@AllArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ShowResponseDTO addShow(@RequestBody ShowRequestDTO showRequestDTO){
        return showService.addShow(showRequestDTO);
    }

    @GetMapping("/search")
    public List<ShowResponseDTO> searchShow(@RequestParam String city){
        return showService.searchShowByCity(city);
    }

    @GetMapping("/{id}")
    public ShowResponseDTO searchShowById(@PathVariable Long id){
       return showService.searchShowById(id);
    }

    @GetMapping("/screen")
    public List<ShowResponseDTO> screenSpecificShow(@RequestParam Long screenId){
       return showService.screenSpecificShow(screenId);
    }

}
