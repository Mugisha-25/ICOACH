package com.example.icoach.repository;

import com.example.icoach.model.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    List<BoardMember> findByActiveTrueOrderByDisplayOrderAsc();
}
