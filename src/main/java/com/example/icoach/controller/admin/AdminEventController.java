package com.example.icoach.controller.admin;

import com.example.icoach.model.Event;
import com.example.icoach.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class AdminEventController {

    private final EventService eventService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("events", eventService.findAll());
        return "admin/events";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("isNew", true);
        return "admin/event-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return eventService.findById(id)
                .map(e -> {
                    model.addAttribute("event", e);
                    model.addAttribute("isNew", false);
                    return "admin/event-form";
                })
                .orElse("redirect:/admin/events");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Event event, RedirectAttributes redirectAttrs) {
        eventService.save(event);
        redirectAttrs.addFlashAttribute("success", "Event saved successfully.");
        return "redirect:/admin/events";
    }

    @PostMapping("/{id}/publish")
    public String togglePublish(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        eventService.togglePublished(id);
        redirectAttrs.addFlashAttribute("success", "Event status updated.");
        return "redirect:/admin/events";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        eventService.delete(id);
        redirectAttrs.addFlashAttribute("success", "Event deleted.");
        return "redirect:/admin/events";
    }
}
