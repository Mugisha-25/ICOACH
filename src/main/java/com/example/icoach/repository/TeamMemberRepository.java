package com.example.icoach.repository;

import com.example.icoach.model.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findByActiveTrueAndStaffTrueOrderByDisplayOrderAsc();
    List<TeamMember> findByActiveTrue();
}
