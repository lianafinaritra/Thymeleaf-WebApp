package com.web.prog4webapp.repository;

import com.web.prog4webapp.model.CNAPS;
import com.web.prog4webapp.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, String> {
    Optional<Phone> findPhoneByPhoneNumber(String phoneNumber);
}
