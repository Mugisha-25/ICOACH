package com.example.icoach.controller.admin;

import com.example.icoach.model.Role;
import com.example.icoach.model.User;
import com.example.icoach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", Role.values());
        return "admin/users";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values());
        model.addAttribute("isNew", true);
        return "admin/user-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return userService.findById(id)
                .map(u -> {
                    model.addAttribute("user", u);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("isNew", false);
                    return "admin/user-form";
                })
                .orElse("redirect:/admin/users");
    }

    @PostMapping("/save")
    public String save(@RequestParam String firstName,
                       @RequestParam String lastName,
                       @RequestParam String email,
                       @RequestParam(required = false) String password,
                       @RequestParam String role,
                       @RequestParam(required = false) Long id,
                       RedirectAttributes ra) {
        if (id == null) {
            userService.register(firstName, lastName, email, password, Role.valueOf(role));
        } else {
            userService.findById(id).ifPresent(u -> {
                u.setFirstName(firstName);
                u.setLastName(lastName);
                u.setEmail(email);
                u.setRole(Role.valueOf(role));
                userService.save(u);
                if (password != null && !password.isBlank()) {
                    userService.changePassword(id, password);
                }
            });
        }
        ra.addFlashAttribute("success", "User saved successfully.");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/toggle")
    public String toggle(@PathVariable Long id, RedirectAttributes ra) {
        userService.toggleEnabled(id);
        ra.addFlashAttribute("success", "User status updated.");
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        userService.delete(id);
        ra.addFlashAttribute("success", "User deleted.");
        return "redirect:/admin/users";
    }
}
