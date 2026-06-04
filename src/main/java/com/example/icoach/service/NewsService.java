package com.example.icoach.service;

import com.example.icoach.model.NewsPost;
import com.example.icoach.repository.NewsPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsPostRepository newsPostRepository;

    public List<NewsPost> findAllPublished() {
        return newsPostRepository.findByPublishedTrueOrderByPublishedAtDesc();
    }

    public List<NewsPost> findAll() {
        return newsPostRepository.findAll();
    }

    public List<NewsPost> findFeatured() {
        return newsPostRepository.findByFeaturedTrueAndPublishedTrue();
    }

    public List<NewsPost> findLatest3() {
        return newsPostRepository.findTop3ByPublishedTrueOrderByPublishedAtDesc();
    }

    public Optional<NewsPost> findBySlug(String slug) {
        return newsPostRepository.findBySlug(slug);
    }

    public Optional<NewsPost> findById(Long id) {
        return newsPostRepository.findById(id);
    }

    public NewsPost save(NewsPost post) {
        if (post.getSlug() == null || post.getSlug().isBlank()) {
            post.setSlug(toSlug(post.getTitle()) + "-" + System.currentTimeMillis());
        }
        if (post.isPublished() && post.getPublishedAt() == null) {
            post.setPublishedAt(LocalDateTime.now());
        }
        return newsPostRepository.save(post);
    }

    public void delete(Long id) {
        newsPostRepository.deleteById(id);
    }

    public void togglePublished(Long id) {
        newsPostRepository.findById(id).ifPresent(p -> {
            p.setPublished(!p.isPublished());
            if (p.isPublished() && p.getPublishedAt() == null) {
                p.setPublishedAt(LocalDateTime.now());
            }
            newsPostRepository.save(p);
        });
    }

    private String toSlug(String title) {
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
