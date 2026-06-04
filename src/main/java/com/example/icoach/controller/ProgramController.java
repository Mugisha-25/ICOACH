package com.example.icoach.controller;

import com.example.icoach.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/programs")
@RequiredArgsConstructor
public class ProgramController {

    private final ProgramService programService;

    @GetMapping
    public String programs(Model model) {
        model.addAttribute("programs", programService.findAllActive());
        return "programs";
    }

    @GetMapping("/{slug}")
    public String programDetail(@PathVariable String slug, Model model) {
        return programService.findBySlug(slug)
                .map(p -> {
                    model.addAttribute("program", p);
                    model.addAttribute("otherPrograms", programService.findAllActive());
                    return "program-detail";
                })
                .orElse("redirect:/programs");
    }
}
