package com.example.icoach.controller.admin;

import com.example.icoach.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/contacts")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class AdminContactController {

    private final ContactService contactService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("messages", contactService.findAll());
        model.addAttribute("newCount", contactService.countNew());
        return "admin/contacts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return contactService.findById(id)
                .map(m -> {
                    contactService.markAsRead(id);
                    model.addAttribute("message", m);
                    return "admin/contact-detail";
                })
                .orElse("redirect:/admin/contacts");
    }

    @PostMapping("/{id}/reply")
    public String markReplied(@PathVariable Long id, RedirectAttributes ra) {
        contactService.markAsReplied(id);
        ra.addFlashAttribute("success", "Message marked as replied.");
        return "redirect:/admin/contacts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        contactService.delete(id);
        ra.addFlashAttribute("success", "Message deleted.");
        return "redirect:/admin/contacts";
    }
}
