package com.example.icoach.controller.admin;

import com.example.icoach.model.BoardMember;
import com.example.icoach.model.TeamMember;
import com.example.icoach.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/team")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
public class AdminTeamController {

    private final TeamService teamService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("staff", teamService.findAll());
        model.addAttribute("board", teamService.findAllBoard());
        return "admin/team";
    }

    @GetMapping("/staff/new")
    public String newStaffForm(Model model) {
        model.addAttribute("member", new TeamMember());
        model.addAttribute("isNew", true);
        return "admin/team-form";
    }

    @GetMapping("/staff/{id}/edit")
    public String editStaffForm(@PathVariable Long id, Model model) {
        return teamService.findTeamById(id)
                .map(m -> {
                    model.addAttribute("member", m);
                    model.addAttribute("isNew", false);
                    return "admin/team-form";
                })
                .orElse("redirect:/admin/team");
    }

    @PostMapping("/staff/save")
    public String saveStaff(@ModelAttribute TeamMember member, RedirectAttributes ra) {
        teamService.saveTeamMember(member);
        ra.addFlashAttribute("success", "Staff member saved.");
        return "redirect:/admin/team";
    }

    @PostMapping("/staff/{id}/delete")
    public String deleteStaff(@PathVariable Long id, RedirectAttributes ra) {
        teamService.deleteTeamMember(id);
        ra.addFlashAttribute("success", "Staff member deleted.");
        return "redirect:/admin/team";
    }

    @GetMapping("/board/new")
    public String newBoardForm(Model model) {
        model.addAttribute("boardMember", new BoardMember());
        model.addAttribute("isNew", true);
        return "admin/board-form";
    }

    @GetMapping("/board/{id}/edit")
    public String editBoardForm(@PathVariable Long id, Model model) {
        return teamService.findBoardById(id)
                .map(m -> {
                    model.addAttribute("boardMember", m);
                    model.addAttribute("isNew", false);
                    return "admin/board-form";
                })
                .orElse("redirect:/admin/team");
    }

    @PostMapping("/board/save")
    public String saveBoard(@ModelAttribute BoardMember member, RedirectAttributes ra) {
        teamService.saveBoardMember(member);
        ra.addFlashAttribute("success", "Board member saved.");
        return "redirect:/admin/team";
    }

    @PostMapping("/board/{id}/delete")
    public String deleteBoard(@PathVariable Long id, RedirectAttributes ra) {
        teamService.deleteBoardMember(id);
        ra.addFlashAttribute("success", "Board member deleted.");
        return "redirect:/admin/team";
    }
}
