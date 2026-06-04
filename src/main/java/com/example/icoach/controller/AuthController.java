package com.example.icoach.controller;

import com.example.icoach.model.Role;
import com.example.icoach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error,
                        @RequestParam(required = false) String logout,
                        Model model) {
        if (error != null) model.addAttribute("error", "Invalid email or password. Please try again.");
        if (logout != null) model.addAttribute("logout", "You have been logged out successfully.");
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttrs) {

        if (!password.equals(confirmPassword)) {
            redirectAttrs.addFlashAttribute("error", "Passwords do not match.");
            return "redirect:/register";
        }

        if (userService.existsByEmail(email)) {
            redirectAttrs.addFlashAttribute("error", "An account with this email already exists.");
            return "redirect:/register";
        }

        userService.register(firstName, lastName, email, password, Role.COMMUNICATIONS);
        redirectAttrs.addFlashAttribute("success", "Account created successfully. Please log in.");
        return "redirect:/login";
    }
}
