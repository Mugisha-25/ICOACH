package com.example.icoach.controller;

import com.example.icoach.model.VolunteerApplication;
import com.example.icoach.service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/volunteer")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;

    @GetMapping
    public String volunteer(Model model) {
        return "volunteer";
    }

    @PostMapping("/apply")
    public String applyVolunteer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(defaultValue = "") String address,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String state,
            @RequestParam(defaultValue = "") String zipCode,
            @RequestParam(defaultValue = "") String skills,
            @RequestParam(defaultValue = "") String interests,
            @RequestParam(defaultValue = "") String availability,
            @RequestParam(defaultValue = "") String motivation,
            @RequestParam(defaultValue = "") String emergencyContactName,
            @RequestParam(defaultValue = "") String emergencyContactPhone,
            @RequestParam(defaultValue = "false") boolean backgroundCheckConsent,
            RedirectAttributes redirectAttrs) {

        VolunteerApplication application = VolunteerApplication.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .phone(phone)
                .address(address)
                .city(city)
                .state(state)
                .zipCode(zipCode)
                .skills(skills)
                .interests(interests)
                .availability(availability)
                .motivation(motivation)
                .emergencyContactName(emergencyContactName)
                .emergencyContactPhone(emergencyContactPhone)
                .backgroundCheckConsent(backgroundCheckConsent)
                .status(VolunteerApplication.VolunteerStatus.PENDING)
                .build();

        volunteerService.save(application);
        redirectAttrs.addFlashAttribute("applicationSubmitted", true);
        return "redirect:/volunteer?applied=true";
    }
}
