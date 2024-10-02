package com.example.th.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.th.model.TimeOffRequest;

public interface TimeOffRequestRepository extends MongoRepository<TimeOffRequest, String> {
    
	// Find leave requests by status (Pending, Approved, Rejected)
	List<TimeOffRequest> findByStatus(String status);
    
    // Find leave requests by employee ID
    List<TimeOffRequest> findByEmployeeId(String employeeId);

    
    
    // Find leave requests by date range
    List<TimeOffRequest> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("{ 'employeeId': ?0, 'startDate': { $gte: ?1 }, 'endDate': { $lte: ?2 } }")
    List<TimeOffRequest> findByEmployeeIdAndMonth(String employeeId, LocalDate startOfMonth, LocalDate endOfMonth);


}
