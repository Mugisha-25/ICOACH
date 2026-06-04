package com.example.icoach.repository;

import com.example.icoach.model.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsPostRepository extends JpaRepository<NewsPost, Long> {
    List<NewsPost> findByPublishedTrueOrderByPublishedAtDesc();
    List<NewsPost> findByFeaturedTrueAndPublishedTrue();
    Optional<NewsPost> findBySlug(String slug);
    List<NewsPost> findByCategory(String category);
    List<NewsPost> findTop3ByPublishedTrueOrderByPublishedAtDesc();
}
