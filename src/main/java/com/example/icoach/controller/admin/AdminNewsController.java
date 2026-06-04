package com.example.icoach.controller.admin;

import com.example.icoach.model.NewsPost;
import com.example.icoach.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/news")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN','COMMUNICATIONS')")
public class AdminNewsController {

    private final NewsService newsService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", newsService.findAll());
        return "admin/news";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new NewsPost());
        model.addAttribute("isNew", true);
        return "admin/news-form";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        return newsService.findById(id)
                .map(p -> {
                    model.addAttribute("post", p);
                    model.addAttribute("isNew", false);
                    return "admin/news-form";
                })
                .orElse("redirect:/admin/news");
    }

    @PostMapping("/save")
    public String save(@ModelAttribute NewsPost post, RedirectAttributes ra) {
        newsService.save(post);
        ra.addFlashAttribute("success", "News post saved.");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/publish")
    public String togglePublish(@PathVariable Long id, RedirectAttributes ra) {
        newsService.togglePublished(id);
        ra.addFlashAttribute("success", "Post status updated.");
        return "redirect:/admin/news";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        newsService.delete(id);
        ra.addFlashAttribute("success", "Post deleted.");
        return "redirect:/admin/news";
    }
}
