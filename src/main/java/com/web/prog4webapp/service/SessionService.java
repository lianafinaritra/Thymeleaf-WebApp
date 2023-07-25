package com.web.prog4webapp.service;

import com.web.prog4webapp.model.Session;
import com.web.prog4webapp.repository.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SessionService {
    private final SessionRepository repository;
    @Transactional
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
    public void disconnect(String sessionId) {
        Optional<Session> session = repository.findById(sessionId);
        if(session.isPresent()){
            repository.delete(session.get());
            System.out.println("Disconnected");
        }
    }
}
