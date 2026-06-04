package com.example.icoach.controller;

import com.example.icoach.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String events(Model model) {
        model.addAttribute("upcomingEvents", eventService.findUpcoming());
        model.addAttribute("allEvents", eventService.findAllPublished());
        return "events";
    }

    @GetMapping("/{id}")
    public String eventDetail(@PathVariable Long id, Model model) {
        return eventService.findById(id)
                .map(e -> {
                    model.addAttribute("event", e);
                    return "event-detail";
                })
                .orElse("redirect:/events");
    }
}
