package com.tm.platform.shows.inventory.service;

import com.tm.platform.repository.mysql.ShowSeatRepository;
import com.tm.platform.shows.inventory.model.SeatStatus;
import com.tm.platform.shows.inventory.model.ShowSeat;
import com.tm.platform.shows.model.Show;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;

    @Transactional
    public void generateSeatsForShow(Show show){
        List<ShowSeat> showSeats = new ArrayList<>();

        // Simple Logic: Generating seats as S1, S2, S3...
        // In a real app, you could use Row/Column logic (A1, A2, B1...)
        for (int i = 1; i <= show.getTotalSeats(); i++) {
            ShowSeat seat = new ShowSeat();
            seat.setShow(show);
            seat.setSeatNumber("S" + i);
            seat.setStatus(SeatStatus.AVAILABLE); // Initial state
            seat.setPrice(show.getPrice());      // Base price from the Show
            showSeats.add(seat);
        }

        showSeatRepository.saveAll(showSeats);
    }

    public List<ShowSeat> getSeatsByShow(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }
}
