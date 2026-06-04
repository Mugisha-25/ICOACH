package com.example.icoach.service;

import com.example.icoach.model.Event;
import com.example.icoach.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> findUpcoming() {
        return eventRepository.findByPublishedTrueAndStartDateAfterOrderByStartDateAsc(LocalDateTime.now());
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findAllPublished() {
        return eventRepository.findByPublishedTrueOrderByStartDateAsc();
    }

    public List<Event> findFeatured() {
        return eventRepository.findByFeaturedTrueAndPublishedTrue();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }

    public void togglePublished(Long id) {
        eventRepository.findById(id).ifPresent(e -> {
            e.setPublished(!e.isPublished());
            eventRepository.save(e);
        });
    }
}
