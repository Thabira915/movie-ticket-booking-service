package com.tm.platform.repository.mysql;

import com.tm.platform.shows.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s JOIN FETCH s.screen sc JOIN FETCH sc.theater t WHERE t.city = :city")
    List<Show> findByCity(String city);

    List<Show> findByScreen(Long screenId);

    @Query("SELECT COUNT(s) > 0 FROM Show s WHERE s.screen.id = :screenId " +
            "AND s.startTime < :newEndTime AND s.endTime > :newStartTime")
    boolean existsOverlappingShow(
            @Param("screenId") Long screenId,
            @Param("newStartTime") LocalDateTime newStartTime,
            @Param("newEndTime") LocalDateTime newEndTime
    );
}
