package com.example.icoach.controller;

import com.example.icoach.model.ContactMessage;
import com.example.icoach.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public String contact(Model model) {
        model.addAttribute("message", new ContactMessage());
        return "contact";
    }

    @PostMapping
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam(defaultValue = "GENERAL") String type,
            @RequestParam(defaultValue = "") String organization,
            RedirectAttributes redirectAttrs) {

        ContactMessage msg = ContactMessage.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .subject(subject)
                .message(message)
                .organization(organization)
                .type(ContactMessage.MessageType.valueOf(type))
                .status(ContactMessage.MessageStatus.NEW)
                .build();

        contactService.save(msg);
        redirectAttrs.addFlashAttribute("success", true);
        return "redirect:/contact?sent=true";
    }
}
