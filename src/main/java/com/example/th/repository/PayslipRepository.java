package com.example.th.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.th.model.Payslip;

public interface PayslipRepository extends MongoRepository<Payslip, String> {
    List<Payslip> findByEmployeeIdAndMonth(String employeeId, String month);
    
// // Find payslips by employee ID
//    List<Payslip> findByEmpId(String empId);

    // Find payslips by month
    List<Payslip> findByMonth(LocalDate month);

    // Find payslips by status (Pending, Approved)
    List<Payslip> findByStatus(String status);

    List<Payslip> findByEmployeeId(String employeeId);
}
