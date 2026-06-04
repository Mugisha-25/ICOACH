package com.example.icoach.controller.admin;

import com.example.icoach.model.Program;
import com.example.icoach.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/programs")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','PROGRAM_MANAGER')")
public class AdminProgramController {

    private final ProgramService programService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("programs", programService.findAll());
        return "admin/programs";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("program", new Program());
        model.addAttribute("isNew", true);
        return "admin/program-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return programService.findById(id)
                .map(p -> {
                    model.addAttribute("program", p);
                    model.addAttribute("isNew", false);
                    return "admin/program-form";
                })
                .orElse("redirect:/admin/programs");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Program program, RedirectAttributes redirectAttrs) {
        programService.save(program);
        redirectAttrs.addFlashAttribute("success", "Program saved successfully.");
        return "redirect:/admin/programs";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        programService.delete(id);
        redirectAttrs.addFlashAttribute("success", "Program deleted.");
        return "redirect:/admin/programs";
    }
}
