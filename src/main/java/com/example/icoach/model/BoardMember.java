package com.example.icoach.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "board_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String title;
    private String profession;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String imageUrl;
    private String term;
    private int displayOrder;
    private boolean active = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
