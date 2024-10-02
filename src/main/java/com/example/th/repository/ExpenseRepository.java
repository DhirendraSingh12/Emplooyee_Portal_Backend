package com.example.th.repository;

import com.example.th.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    // Find expenses by employee ID
    List<Expense> findByEmployeeId(String employeeId);

    // Find expenses by status
    List<Expense> findByStatus(String status);

    // Find expenses by date range
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("{ 'employeeId': ?0, 'expenseDate': { $gte: ?1, $lte: ?2 } }")
    List<Expense> findByEmployeeIdAndMonth(String employeeId, LocalDate startOfMonth, LocalDate endOfMonth);
}