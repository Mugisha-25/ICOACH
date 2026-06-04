package com.example.icoach.controller.admin;

import com.example.icoach.model.*;
import com.example.icoach.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final UserService userService;
    private final ProgramService programService;
    private final EventService eventService;
    private final DonationService donationService;
    private final VolunteerService volunteerService;
    private final ContactService contactService;
    private final NewsService newsService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Authentication auth, Model model) {
        model.addAttribute("totalPrograms", programService.findAll().size());
        model.addAttribute("totalEvents", eventService.findAll().size());
        model.addAttribute("totalDonations", donationService.getTotalDonations());
        model.addAttribute("monthlyDonations", donationService.getTotalThisMonth());
        model.addAttribute("pendingVolunteers", volunteerService.countByStatus(VolunteerApplication.VolunteerStatus.PENDING));
        model.addAttribute("newMessages", contactService.countNew());
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("recentDonations", donationService.findByStatus(Donation.DonationStatus.COMPLETED).stream().limit(5).toList());
        model.addAttribute("recentMessages", contactService.findByStatus(ContactMessage.MessageStatus.NEW).stream().limit(5).toList());
        model.addAttribute("currentUser", auth.getName());
        return "admin/dashboard";
    }
}
