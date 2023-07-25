package com.web.prog4webapp.repository;

import com.web.prog4webapp.model.Phone;
import com.web.prog4webapp.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    Optional<Session> findSessionById(String sessionId);
}
