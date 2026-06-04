package com.example.icoach.repository;

import com.example.icoach.model.ImpactNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImpactNumberRepository extends JpaRepository<ImpactNumber, Long> {
    List<ImpactNumber> findByActiveTrueOrderByDisplayOrderAsc();
}
