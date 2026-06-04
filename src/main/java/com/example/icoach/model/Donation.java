package com.example.icoach.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donation {

    public enum DonationType { ONE_TIME, MONTHLY }
    public enum DonationStatus { PENDING, COMPLETED, FAILED, REFUNDED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String donorFirstName;
    private String donorLastName;
    private String donorEmail;
    private String donorPhone;
    private String donorAddress;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private DonationType donationType = DonationType.ONE_TIME;

    @Enumerated(EnumType.STRING)
    private DonationStatus status = DonationStatus.PENDING;

    private String receiptNumber;
    private String transactionId;
    private String purpose;
    private String paymentMethod;
    private boolean anonymous = false;
    private boolean receiptSent = false;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (receiptNumber == null) {
            receiptNumber = "ICC-" + System.currentTimeMillis();
        }
    }

    public String getDonorFullName() {
        if (anonymous) return "Anonymous";
        return donorFirstName + " " + donorLastName;
    }
}
