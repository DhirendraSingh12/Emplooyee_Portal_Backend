package com.example.th.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.example.th.model.My_Document;


public interface DocumentRepository extends MongoRepository<My_Document, String> {
	 // Find all documents by employee ID
    List<My_Document> findByEmployeeId(String employeeId);

    // Find documents by verify status
    List<My_Document> findByVerifyStatus(String verifyStatus);
}