package com.web.prog4webapp.service;

import com.web.prog4webapp.controller.model.Credentials;
import com.web.prog4webapp.model.Employee;
import com.web.prog4webapp.model.Session;
import com.web.prog4webapp.repository.EmployeeRepository;
import com.web.prog4webapp.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository repository;
    public Session getSessionById(String sessionId) {
        Optional<Session> session = repository.findSessionById(sessionId);
        if(session.isPresent()){
            return session.get();
        }
        return new Session();
    }

    public Session createOrUpdateSession(Session restSession){
        return repository.save(restSession);
    }
}
