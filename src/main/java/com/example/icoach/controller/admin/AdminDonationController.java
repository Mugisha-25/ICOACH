package com.example.icoach.controller.admin;

import com.example.icoach.model.Donation;
import com.example.icoach.service.DonationService;
import com.example.icoach.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/donations")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','FINANCE')")
public class AdminDonationController {

    private final DonationService donationService;
    private final ExportService exportService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("donations", donationService.findAll());
        model.addAttribute("totalAmount", donationService.getTotalDonations());
        model.addAttribute("monthlyAmount", donationService.getTotalThisMonth());
        return "admin/donations";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return donationService.findById(id)
                .map(d -> {
                    model.addAttribute("donation", d);
                    return "admin/donation-detail";
                })
                .orElse("redirect:/admin/donations");
    }

    @PostMapping("/{id}/complete")
    public String complete(@PathVariable Long id, RedirectAttributes ra) {
        donationService.complete(id);
        ra.addFlashAttribute("success", "Donation marked as completed.");
        return "redirect:/admin/donations";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        donationService.delete(id);
        ra.addFlashAttribute("success", "Donation deleted.");
        return "redirect:/admin/donations";
    }

    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf() throws Exception {
        List<Donation> donations = donationService.findAll();
        byte[] pdf = exportService.exportDonationsToPdf(donations);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=donations-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws Exception {
        List<Donation> donations = donationService.findAll();
        byte[] xlsx = exportService.exportDonationsToExcel(donations);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=donations-report.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(xlsx);
    }
}
