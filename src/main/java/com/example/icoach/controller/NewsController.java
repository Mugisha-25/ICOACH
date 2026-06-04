package com.example.icoach.controller;

import com.example.icoach.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public String news(Model model) {
        model.addAttribute("posts", newsService.findAllPublished());
        model.addAttribute("featured", newsService.findFeatured());
        return "news";
    }

    @GetMapping("/{slug}")
    public String newsDetail(@PathVariable String slug, Model model) {
        return newsService.findBySlug(slug)
                .map(post -> {
                    model.addAttribute("post", post);
                    model.addAttribute("recentPosts", newsService.findLatest3());
                    return "news-detail";
                })
                .orElse("redirect:/news");
    }
}
