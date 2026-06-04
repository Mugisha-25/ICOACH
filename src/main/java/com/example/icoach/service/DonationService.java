package com.example.icoach.service;

import com.example.icoach.model.Donation;
import com.example.icoach.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DonationService {

    private final DonationRepository donationRepository;

    public Donation save(Donation donation) {
        return donationRepository.save(donation);
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Optional<Donation> findById(Long id) {
        return donationRepository.findById(id);
    }

    public Optional<Donation> findByReceiptNumber(String receiptNumber) {
        return donationRepository.findByReceiptNumber(receiptNumber);
    }

    public List<Donation> findByEmail(String email) {
        return donationRepository.findByDonorEmailOrderByCreatedAtDesc(email);
    }

    public List<Donation> findByStatus(Donation.DonationStatus status) {
        return donationRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public List<Donation> findByDateRange(LocalDateTime start, LocalDateTime end) {
        return donationRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    public BigDecimal getTotalDonations() {
        return donationRepository.getTotalDonations();
    }

    public BigDecimal getTotalThisMonth() {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        return donationRepository.getTotalDonationsSince(startOfMonth);
    }

    public long countByStatus(Donation.DonationStatus status) {
        return donationRepository.countByStatus(status);
    }

    public void complete(Long id) {
        donationRepository.findById(id).ifPresent(d -> {
            d.setStatus(Donation.DonationStatus.COMPLETED);
            donationRepository.save(d);
        });
    }

    public void delete(Long id) {
        donationRepository.deleteById(id);
    }
}
