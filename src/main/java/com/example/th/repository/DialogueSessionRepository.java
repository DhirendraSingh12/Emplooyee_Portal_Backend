package com.example.th.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.th.model.DialogueSession;

import java.util.List;

public interface DialogueSessionRepository extends MongoRepository<DialogueSession, String> {
    List<DialogueSession> findByEmployeeId(String employeeId);
    
    // List<DialogueSession> findByEmployeeId(Long employeeId);
}