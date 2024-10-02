package com.example.th.repository;

import com.example.th.model.CompanyPolicy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CompanyPolicyRepository extends MongoRepository<CompanyPolicy, String> {

    // Find policies by name
    List<CompanyPolicy> findByPolicyNameContaining(String policyName);

    // Find policies uploaded on a specific date
    List<CompanyPolicy> findByUploadDate(LocalDate uploadDate);
    
    @Query("{ 'current': true }")
	String findCurrentPolicy();
}