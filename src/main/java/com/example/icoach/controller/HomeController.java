package com.example.icoach.controller;

import com.example.icoach.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProgramService programService;
    private final EventService eventService;
    private final NewsService newsService;
    private final ImpactService impactService;
    private final ContactService contactService;

    @GetMapping({"", "/", "/home"})
    public String home(Model model) {
        model.addAttribute("programs", programService.findAllActive());
        model.addAttribute("events", eventService.findUpcoming());
        model.addAttribute("latestNews", newsService.findLatest3());
        model.addAttribute("impactNumbers", impactService.findAllActive());
        return "index";
    }

    @PostMapping("/api/newsletter/subscribe")
    public String subscribe(
            @RequestParam String email,
            @RequestParam(defaultValue = "") String firstName,
            RedirectAttributes redirectAttrs) {
        String result = contactService.subscribeNewsletter(email, firstName);
        if ("success".equals(result)) {
            redirectAttrs.addFlashAttribute("newsletterSuccess", true);
        } else {
            redirectAttrs.addFlashAttribute("newsletterAlreadySubscribed", true);
        }
        return "redirect:/#newsletter";
    }
}
