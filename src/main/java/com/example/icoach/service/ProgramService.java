package com.example.icoach.service;

import com.example.icoach.model.Program;
import com.example.icoach.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;

    public List<Program> findAllActive() {
        return programRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public Optional<Program> findById(Long id) {
        return programRepository.findById(id);
    }

    public Optional<Program> findBySlug(String slug) {
        return programRepository.findBySlug(slug);
    }

    public Program save(Program program) {
        if (program.getSlug() == null || program.getSlug().isBlank()) {
            program.setSlug(toSlug(program.getName()));
        }
        return programRepository.save(program);
    }

    public void delete(Long id) {
        programRepository.deleteById(id);
    }

    private String toSlug(String name) {
        return name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-");
    }
}
