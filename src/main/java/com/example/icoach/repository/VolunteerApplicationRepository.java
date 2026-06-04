package com.example.icoach.repository;

import com.example.icoach.model.VolunteerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerApplicationRepository extends JpaRepository<VolunteerApplication, Long> {
    List<VolunteerApplication> findByStatusOrderByCreatedAtDesc(VolunteerApplication.VolunteerStatus status);
    long countByStatus(VolunteerApplication.VolunteerStatus status);
    boolean existsByEmail(String email);
}
