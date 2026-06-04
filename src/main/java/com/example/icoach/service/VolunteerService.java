package com.example.icoach.service;

import com.example.icoach.model.VolunteerApplication;
import com.example.icoach.repository.VolunteerApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class VolunteerService {

    private final VolunteerApplicationRepository volunteerRepository;

    public VolunteerApplication save(VolunteerApplication application) {
        return volunteerRepository.save(application);
    }

    public List<VolunteerApplication> findAll() {
        return volunteerRepository.findAll();
    }

    public Optional<VolunteerApplication> findById(Long id) {
        return volunteerRepository.findById(id);
    }

    public List<VolunteerApplication> findByStatus(VolunteerApplication.VolunteerStatus status) {
        return volunteerRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public long countByStatus(VolunteerApplication.VolunteerStatus status) {
        return volunteerRepository.countByStatus(status);
    }

    public void updateStatus(Long id, VolunteerApplication.VolunteerStatus status) {
        volunteerRepository.findById(id).ifPresent(v -> {
            v.setStatus(status);
            v.setReviewedAt(LocalDateTime.now());
            volunteerRepository.save(v);
        });
    }

    public void logHours(Long id, Double hours) {
        volunteerRepository.findById(id).ifPresent(v -> {
            v.setHoursLogged(v.getHoursLogged() + hours);
            volunteerRepository.save(v);
        });
    }

    public void delete(Long id) {
        volunteerRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return volunteerRepository.existsByEmail(email);
    }
}
