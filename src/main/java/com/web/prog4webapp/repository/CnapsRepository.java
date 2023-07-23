package com.web.prog4webapp.repository;

import com.web.prog4webapp.model.CNAPS;
import com.web.prog4webapp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CnapsRepository extends JpaRepository<CNAPS, String> {
    Optional<CNAPS> findCNAPSByCnaps(String cnaps);
}
