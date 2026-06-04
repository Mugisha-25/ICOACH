package com.example.icoach.controller;

import com.example.icoach.model.Donation;
import com.example.icoach.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/donate")
@RequiredArgsConstructor
public class DonationController {

    private final DonationService donationService;

    @GetMapping
    public String donate(Model model) {
        return "donate";
    }

    @PostMapping
    public String processDonation(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam(defaultValue = "") String phone,
            @RequestParam(defaultValue = "") String address,
            @RequestParam BigDecimal amount,
            @RequestParam String donationType,
            @RequestParam(defaultValue = "General Support") String purpose,
            @RequestParam(defaultValue = "false") boolean anonymous,
            RedirectAttributes redirectAttrs) {

        Donation donation = Donation.builder()
                .donorFirstName(firstName)
                .donorLastName(lastName)
                .donorEmail(email)
                .donorPhone(phone)
                .donorAddress(address)
                .amount(amount)
                .donationType(Donation.DonationType.valueOf(donationType))
                .purpose(purpose)
                .anonymous(anonymous)
                .status(Donation.DonationStatus.COMPLETED)
                .paymentMethod("Online")
                .build();

        Donation saved = donationService.save(donation);
        redirectAttrs.addFlashAttribute("receiptNumber", saved.getReceiptNumber());
        redirectAttrs.addFlashAttribute("donationAmount", amount);
        redirectAttrs.addFlashAttribute("donorName", anonymous ? "Anonymous" : firstName + " " + lastName);
        return "redirect:/donate/thank-you";
    }

    @GetMapping("/thank-you")
    public String thankYou(Model model) {
        return "donate-thankyou";
    }

    @GetMapping("/receipt/{receiptNumber}")
    public String receipt(@PathVariable String receiptNumber, Model model) {
        return donationService.findByReceiptNumber(receiptNumber)
                .map(d -> {
                    model.addAttribute("donation", d);
                    return "donate-receipt";
                })
                .orElse("redirect:/donate");
    }
}
