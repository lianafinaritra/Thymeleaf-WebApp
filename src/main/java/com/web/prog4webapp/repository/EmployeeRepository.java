package com.web.prog4webapp.repository;

import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.model.Phone;
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
            "OR LOWER(role) LIKE LOWER(CONCAT('%', :word, '%')))",
            nativeQuery = true)
    List<Employee> searchByWord(String word);

    @Query(value = "SELECT * FROM employee " +
            "WHERE sex = :word ",
            nativeQuery = true)
    List<Employee> searchBySex(String word);

    @Query("SELECT e FROM Employee e WHERE EXISTS (SELECT p FROM e.phone p WHERE p.phoneNumber LIKE ?1%)")
    List<Employee> findEmployeesByCode(String code);

    List<Employee> findAll(Sort sort);
    Optional<Employee> findEmployeeByUserNameAndPassword(String userName, String password);
}
