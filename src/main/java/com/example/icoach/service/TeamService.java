package com.example.icoach.service;

import com.example.icoach.model.BoardMember;
import com.example.icoach.model.TeamMember;
import com.example.icoach.repository.BoardMemberRepository;
import com.example.icoach.repository.TeamMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamService {

    private final TeamMemberRepository teamMemberRepository;
    private final BoardMemberRepository boardMemberRepository;

    public List<TeamMember> findActiveStaff() {
        return teamMemberRepository.findByActiveTrueAndStaffTrueOrderByDisplayOrderAsc();
    }

    public List<TeamMember> findAll() {
        return teamMemberRepository.findAll();
    }

    public Optional<TeamMember> findTeamById(Long id) {
        return teamMemberRepository.findById(id);
    }

    public TeamMember saveTeamMember(TeamMember member) {
        return teamMemberRepository.save(member);
    }

    public void deleteTeamMember(Long id) {
        teamMemberRepository.deleteById(id);
    }

    public List<BoardMember> findActiveBoard() {
        return boardMemberRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    public List<BoardMember> findAllBoard() {
        return boardMemberRepository.findAll();
    }

    public Optional<BoardMember> findBoardById(Long id) {
        return boardMemberRepository.findById(id);
    }

    public BoardMember saveBoardMember(BoardMember member) {
        return boardMemberRepository.save(member);
    }

    public void deleteBoardMember(Long id) {
        boardMemberRepository.deleteById(id);
    }
}
