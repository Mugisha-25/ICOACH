package com.example.icoach.model;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "impact_numbers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImpactNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    @Column(name = "stat_value")
    private String value;

    private String description;
    private String iconClass;
    private int displayOrder;
    private boolean active = true;
}
