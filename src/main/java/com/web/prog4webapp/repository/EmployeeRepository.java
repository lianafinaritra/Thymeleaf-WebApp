package com.web.prog4webapp.repository;

import com.web.prog4webapp.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query(value = "SELECT * FROM employee " +
            "WHERE (LOWER(last_name) LIKE LOWER(CONCAT('%', :word, '%')) " +
            "OR LOWER(first_name) LIKE LOWER(CONCAT('%', :word, '%')) " +
            "OR sex = :word " +
            "OR LOWER(role) LIKE LOWER(CONCAT('%', :word, '%')))",
            nativeQuery = true)
    List<Employee> searchByWord(String word);

    List<Employee> findAll(Sort sort);
    Optional<Employee> findEmployeeByMatricule(String matricule);
}
