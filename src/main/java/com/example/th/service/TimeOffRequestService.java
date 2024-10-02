//package com.example.th.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import com.example.th.exception.ResourceNotFoundException;
//import com.example.th.model.Employee;
//import com.example.th.model.TimeOffRequest;
//import com.example.th.repository.EmployeeRepository;
//import com.example.th.repository.TimeOffRequestRepository;
//
//import java.time.LocalDate;
//import java.time.temporal.TemporalAdjusters;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TimeOffRequestService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//    
//    @Autowired
//    private TimeOffRequestRepository timeOffRequestRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    
//    private String getAuthenticatedEmployeeId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            // Assuming that the employee ID is the username or can be retrieved from userDetails
//            return userDetails.getUsername(); // or any method to get employee ID
//        }
//
//        throw new RuntimeException("No authenticated employee found");
//    }
//
////    // Submit a new leave request for an employee
////    public TimeOffRequest submitLeaveRequest(String employeeId, TimeOffRequest leaveRequest) {
////        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
////        if (employee.isPresent()) {
////            leaveRequest.setStatus("PENDING"); // Set initial status to "PENDING"
////            return timeOffRequestRepository.save(leaveRequest);
////        } else {
////            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
////        }
////    }
//
//    // Approve a leave request
////    public TimeOffRequest approveLeaveRequest(String leaveRequestId, String approvedBy) {
////        TimeOffRequest leaveRequest = timeOffRequestRepository.findById(leaveRequestId)
////                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with ID: " + leaveRequestId));
////
////        leaveRequest.setStatus("APPROVED");
////        // Set approver information if needed
////        // leaveRequest.setApprovedBy(approvedBy);
////        return timeOffRequestRepository.save(leaveRequest);
////    } 
//    public boolean approveTimeOffRequest(String timeOffRequestId) {
//        // Find the time-off request by ID
//        Optional<TimeOffRequest> timeOffRequestOptional = timeOffRequestRepository.findById(timeOffRequestId);
//        
//        if (timeOffRequestOptional.isPresent()) {
//            TimeOffRequest timeOffRequest = timeOffRequestOptional.get();
//            
//            // Set status to Approved
//            timeOffRequest.setStatus("Approved");
//            
//            // Save the updated time-off request
//            timeOffRequestRepository.save(timeOffRequest);
//            return true;
//        }
//
//        return false;
//    }
//
//    // Reject a leave request
//    public TimeOffRequest rejectLeaveRequest(String leaveRequestId, String rejectedBy) {
//        TimeOffRequest leaveRequest = timeOffRequestRepository.findById(leaveRequestId)
//                .orElseThrow(() -> new ResourceNotFoundException("Leave request not found with ID: " + leaveRequestId));
//
//        leaveRequest.setStatus("REJECTED");
//        // Set rejector information if needed
//        // leaveRequest.setRejectedBy(rejectedBy);
//        return timeOffRequestRepository.save(leaveRequest);
//    }
//
//    // Filter leave requests by their status
//    public List<TimeOffRequest> filterLeaveRequestsByStatus(String status) {
//        return timeOffRequestRepository.findByStatus(status);
//    }
//
//    // Get leave requests for a specific employee
//    public List<TimeOffRequest> getLeaveRequestsForEmployee(String employeeId) {
//        return timeOffRequestRepository.findByEmployeeId(employeeId);
//    }
//
//    // Get leave requests for an employee within a specific month
//    public List<TimeOffRequest> getByEmployeeIdAndMonth(String empId, LocalDate month) {
//        LocalDate startOfMonth = month.withDayOfMonth(1);
//        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
//        return timeOffRequestRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
//    }
//
//    // Create a new time off request
//    public TimeOffRequest createTimeOffRequest(TimeOffRequest timeOffRequest) {
//        // Initial status or any other default values can be set here if necessary
//    	timeOffRequest.setLeaveId(sequenceGeneratorService.generateSequence(TimeOffRequest.class.getSimpleName()));
//        return timeOffRequestRepository.save(timeOffRequest);
//    }
//
//    // Get all time off requests
//    public List<TimeOffRequest> getAllTimeOffRequests() {
//        return timeOffRequestRepository.findAll();
//    }
//
//    // Get time off requests by status
//    public List<TimeOffRequest> getTimeOffRequestsByStatus(String status) {
//        return timeOffRequestRepository.findByStatus(status);
//    }
//
//    public TimeOffRequest submitLeaveRequest(TimeOffRequest leaveRequest) {
//        // Fetch the authenticated employee's ID
//        String employeeId = getAuthenticatedEmployeeId(); // This method retrieves the employee ID from the logged-in user
//        
//        // Fetch employee details using the employee ID
//        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
//        
//        if (employee.isPresent()) {
//            // Set employee details in the leave request
//            leaveRequest.setEmployeeId(employeeId);
//           // leaveRequest.setEmployeeName(employee.get().getName()); // Assuming Employee entity has a getName() method
//
//            // Set the leave request status to "PENDING"
//            leaveRequest.setStatus("PENDING");
//            
//            // Save and return the leave request
//            return timeOffRequestRepository.save(leaveRequest);
//        } else {
//            // If the employee is not found, throw an exception
//            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
//        }
//    }
//    
//    public boolean rejectTimeOffRequest(String leaveId) {
//        Optional<TimeOffRequest> optionalRequest = timeOffRequestRepository.findById(leaveId);
//        
//        if (optionalRequest.isPresent()) {
//            TimeOffRequest request = optionalRequest.get();
//            request.setStatus("Rejected");
//            timeOffRequestRepository.save(request);
//            return true; // Time-off request rejected
//        }
//        
//        return false; // Time-off request not found
//    }
//
//	
//}


package com.example.th.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Employee;
import com.example.th.model.TimeOffRequest;
import com.example.th.repository.EmployeeRepository;
import com.example.th.repository.TimeOffRequestRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
public class TimeOffRequestService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    
    @Autowired
    private TimeOffRequestRepository timeOffRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String getAuthenticatedEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername(); // Assuming the employee ID is the username
        }

        throw new RuntimeException("No authenticated employee found");
    }

    // Create a new time off request
    public TimeOffRequest createTimeOffRequest(TimeOffRequest timeOffRequest) {
        timeOffRequest.setLeaveId(sequenceGeneratorService.generateSequence(TimeOffRequest.class.getSimpleName()));
        timeOffRequest.setStatus("PENDING"); // Set initial status to PENDING
        return timeOffRequestRepository.save(timeOffRequest);
    }

    // Get all time off requests
    public List<TimeOffRequest> getAllTimeOffRequests() {
        return timeOffRequestRepository.findAll();
    }

    // Get time off requests by leaveId
    public TimeOffRequest getTimeOffRequestById(String leaveId) {
        return timeOffRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("Time off request not found with ID: " + leaveId));
    }

    // Update (approve) a time off request
    public TimeOffRequest approveTimeOffRequest(String leaveId) {
        TimeOffRequest timeOffRequest = getTimeOffRequestById(leaveId);
        timeOffRequest.setStatus("Approved");
        return timeOffRequestRepository.save(timeOffRequest);
    }

    // Reject a time off request
    public TimeOffRequest rejectTimeOffRequest(String leaveId) {
        TimeOffRequest timeOffRequest = getTimeOffRequestById(leaveId);
        timeOffRequest.setStatus("Rejected");
        return timeOffRequestRepository.save(timeOffRequest);
    }

    // Filter leave requests by their status
    public List<TimeOffRequest> filterLeaveRequestsByStatus(String status) {
        return timeOffRequestRepository.findByStatus(status);
    }

    // Get leave requests for a specific employee
    public List<TimeOffRequest> getLeaveRequestsForEmployee(String employeeId) {
        return timeOffRequestRepository.findByEmployeeId(employeeId);
    }

    // Get leave requests for an employee within a specific month
    public List<TimeOffRequest> getByEmployeeIdAndMonth(String empId, LocalDate month) {
        LocalDate startOfMonth = month.withDayOfMonth(1);
        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
        return timeOffRequestRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
    }

    public TimeOffRequest submitLeaveRequest(TimeOffRequest leaveRequest) {
        String employeeId = getAuthenticatedEmployeeId(); // Fetch authenticated employee's ID
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        
        if (employee.isPresent()) {
            leaveRequest.setEmployeeId(employeeId);
            leaveRequest.setStatus("PENDING");
            leaveRequest.setLeaveId(sequenceGeneratorService.generateSequence(TimeOffRequest.class.getSimpleName())); // Generate leaveId
            return timeOffRequestRepository.save(leaveRequest);
        } else {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
    }
    
  // Get time off requests by status
    public List<TimeOffRequest> getTimeOffRequestsByStatus(String status) {
    	return timeOffRequestRepository.findByStatus(status);
    }
    
}

