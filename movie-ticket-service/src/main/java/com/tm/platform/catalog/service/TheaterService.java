package com.tm.platform.catalog.service;

import com.tm.platform.catalog.model.Theater;
import com.tm.platform.repository.mysql.TheaterRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public Theater addTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    public List<Theater> addTheaterInBulk(List<Theater> theaters) {
        return theaterRepository.saveAll(theaters);
    }

    public List<Theater> getAllTheaters(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return theaterRepository.findAll();
    }

    public List<Theater> searchTheaterByCity(String city, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return theaterRepository.findByCityIgnoreCase(city, pageable);
    }

    public Theater searchTheaterById(Long theaterId) {
        return theaterRepository.findById(theaterId).orElse(null);
    }
}
