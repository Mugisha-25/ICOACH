package com.example.icoach.controller.admin;

import com.example.icoach.model.ImpactNumber;
import com.example.icoach.service.ImpactService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/impact")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class AdminImpactController {

    private final ImpactService impactService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("numbers", impactService.findAll());
        return "admin/impact";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("impact", new ImpactNumber());
        model.addAttribute("isNew", true);
        return "admin/impact-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return impactService.findById(id)
                .map(n -> {
                    model.addAttribute("impact", n);
                    model.addAttribute("isNew", false);
                    return "admin/impact-form";
                })
                .orElse("redirect:/admin/impact");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ImpactNumber impact, RedirectAttributes ra) {
        impactService.save(impact);
        ra.addFlashAttribute("success", "Impact number saved.");
        return "redirect:/admin/impact";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        impactService.delete(id);
        ra.addFlashAttribute("success", "Impact number deleted.");
        return "redirect:/admin/impact";
    }
}
