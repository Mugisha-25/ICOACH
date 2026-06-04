package com.example.icoach.controller;

import com.example.icoach.service.ImpactService;
import com.example.icoach.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AboutController {

    private final TeamService teamService;
    private final ImpactService impactService;

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("staff", teamService.findActiveStaff());
        model.addAttribute("board", teamService.findActiveBoard());
        model.addAttribute("impactNumbers", impactService.findAllActive());
        return "about";
    }
}
