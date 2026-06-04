package com.example.icoach.service;

import com.example.icoach.model.ImpactNumber;
import com.example.icoach.repository.ImpactNumberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImpactService {

    private final ImpactNumberRepository impactNumberRepository;

    public List<ImpactNumber> findAllActive() {
        return impactNumberRepository.findByActiveTrueOrderByDisplayOrderAsc();
    }

    public List<ImpactNumber> findAll() {
        return impactNumberRepository.findAll();
    }

    public Optional<ImpactNumber> findById(Long id) {
        return impactNumberRepository.findById(id);
    }

    public ImpactNumber save(ImpactNumber impactNumber) {
        return impactNumberRepository.save(impactNumber);
    }

    public void delete(Long id) {
        impactNumberRepository.deleteById(id);
    }
}
