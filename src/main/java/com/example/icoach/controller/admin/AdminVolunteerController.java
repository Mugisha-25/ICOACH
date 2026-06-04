package com.example.icoach.controller.admin;

import com.example.icoach.model.VolunteerApplication;
import com.example.icoach.service.ExportService;
import com.example.icoach.service.VolunteerService;
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
@RequestMapping("/admin/volunteers")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','VOLUNTEER_COORDINATOR')")
public class AdminVolunteerController {

    private final VolunteerService volunteerService;
    private final ExportService exportService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("volunteers", volunteerService.findAll());
        model.addAttribute("pendingCount", volunteerService.countByStatus(VolunteerApplication.VolunteerStatus.PENDING));
        model.addAttribute("activeCount", volunteerService.countByStatus(VolunteerApplication.VolunteerStatus.ACTIVE));
        return "admin/volunteers";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return volunteerService.findById(id)
                .map(v -> {
                    model.addAttribute("volunteer", v);
                    return "admin/volunteer-detail";
                })
                .orElse("redirect:/admin/volunteers");
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes ra) {
        volunteerService.updateStatus(id, VolunteerApplication.VolunteerStatus.valueOf(status));
        ra.addFlashAttribute("success", "Volunteer status updated.");
        return "redirect:/admin/volunteers/" + id;
    }

    @PostMapping("/{id}/hours")
    public String logHours(@PathVariable Long id,
                           @RequestParam Double hours,
                           RedirectAttributes ra) {
        volunteerService.logHours(id, hours);
        ra.addFlashAttribute("success", "Hours logged successfully.");
        return "redirect:/admin/volunteers/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        volunteerService.delete(id);
        ra.addFlashAttribute("success", "Volunteer record deleted.");
        return "redirect:/admin/volunteers";
    }

    @GetMapping("/export/excel")
    public ResponseEntity<byte[]> exportExcel() throws Exception {
        List<VolunteerApplication> volunteers = volunteerService.findAll();
        byte[] xlsx = exportService.exportVolunteersToExcel(volunteers);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=volunteers-report.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(xlsx);
    }
}
