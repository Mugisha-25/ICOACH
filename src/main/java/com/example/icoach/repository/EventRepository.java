package com.example.icoach.repository;

import com.example.icoach.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPublishedTrueOrderByStartDateAsc();
    List<Event> findByPublishedTrueAndStartDateAfterOrderByStartDateAsc(LocalDateTime date);
    List<Event> findByFeaturedTrueAndPublishedTrue();
    List<Event> findByPublishedTrueAndStartDateBetweenOrderByStartDateAsc(LocalDateTime start, LocalDateTime end);
}
