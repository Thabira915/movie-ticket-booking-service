package com.tm.platform.repository.mysql;

import com.tm.platform.catalog.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    List<Screen> findByTheaterId(Long theaterId);

    // 2. Performance Optimized Fetch
    // Join Fetch prevents the N+1 problem by getting Screen and Theater in 1 query
    @Query("SELECT s FROM Screen s JOIN FETCH s.theater WHERE s.id = :id")
    Screen findByIdWithTheater(@Param("id") Long id);

    // 3. Search screens by City (Cross-table join)
    @Query("SELECT s FROM Screen s WHERE s.theater.city = :city")
    List<Screen> findByTheaterCity(@Param("city") String city);
}

