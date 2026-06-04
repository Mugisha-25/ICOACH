package com.example.icoach.repository;

import com.example.icoach.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByReceiptNumber(String receiptNumber);
    List<Donation> findByStatusOrderByCreatedAtDesc(Donation.DonationStatus status);
    List<Donation> findByDonorEmailOrderByCreatedAtDesc(String email);
    List<Donation> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.status = 'COMPLETED'")
    BigDecimal getTotalDonations();

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d WHERE d.status = 'COMPLETED' AND d.createdAt >= :since")
    BigDecimal getTotalDonationsSince(LocalDateTime since);

    long countByStatus(Donation.DonationStatus status);
}
