package com.example.icoach.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "team_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String imageUrl;
    private String email;
    private String linkedin;
    private String twitter;
    private int displayOrder;
    private boolean active = true;
    private boolean staff = true;

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
