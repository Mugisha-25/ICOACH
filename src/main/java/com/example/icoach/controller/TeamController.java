package com.example.icoach.controller;

import com.example.icoach.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/team")
    public String team(Model model) {
        model.addAttribute("staff", teamService.findActiveStaff());
        model.addAttribute("board", teamService.findActiveBoard());
        return "team";
    }
}
