package com.example.th.controller;


import com.example.th.model.My_Document;
import com.example.th.model.TimeOffRequest;
import com.example.th.model.Timesheet;
import com.example.th.repository.EmployeeRepository;
import com.example.th.model.DialogueSession;
import com.example.th.model.Employee;
import com.example.th.model.Expense;
import com.example.th.model.Payslip;
import com.example.th.model.Perk;
import com.example.th.service.DocumentService;
import com.example.th.service.TimeOffRequestService;
import com.example.th.service.TimesheetService;
import com.example.th.utils.JwtTokenUtil;
import com.example.th.service.ExpenseService;
import com.example.th.service.PayslipService;
import com.example.th.service.PerkService;
import com.example.th.service.CompanyPolicyService;
import com.example.th.service.DialogueSessionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil; 
	
	@Autowired
    private EmployeeRepository employeeRepository;

    
    @Autowired
    private DocumentService documentService;

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @Autowired
    private TimesheetService timesheetService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private PerkService perkService;

    @Autowired
    private CompanyPolicyService companyPolicyService;
    
    @Autowired
    private DialogueSessionService service;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
    public EmployeeController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    // 1. Employee Login using Employee ID and Password
//    @PostMapping("/login")
//    public String loginEmployee(@RequestBody Employee employee, Model model) {
//        try {
//            // Authentication logic
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(employee.getEmployeeId(), employee.getPassword());
//
//            Authentication authentication = authenticationManager.authenticate(authToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Fetch employee details and set in SecurityContext
//            EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
//            String employeeId = employeeDetails.getEmployeeId();
//            String employeeName = employeeDetails.getEmployeeName();
//
//            // Store employee details in the session for further use
//            model.addAttribute("employeeId", employeeId);
//            model.addAttribute("employeeName", employeeName);
//
//            // Redirect to employee dashboard after successful login
//            return "redirect:/employee/dashboard";
//
//        } catch (Exception e) {
//            // If login fails, return to login page with error
//            model.addAttribute("error", "Invalid Employee ID or password");
//            return "employee-login";
//        }
//    }
//    
    
    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody Employee request) {
        try {
            // Log the incoming request
            System.out.println("Login attempt for Employee ID: " + request.getEmployeeId());

            // Fetch the employee from the repository using employeeId
            Optional<Employee> optionalEmployee = employeeRepository.findByEmployeeId(request.getEmployeeId());

            // Check if employee exists
            if (optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                System.out.println("Employee found: " + existingEmployee);

                // Use PasswordEncoder to match the password securely
                boolean passwordMatches = passwordEncoder.matches(request.getPassword(), existingEmployee.getPassword());
                System.out.println("Password matches: " + passwordMatches);

                if (passwordMatches) {
                    // Generate JWT token with all employee details
                    String token = jwtTokenUtil.generateTokenWithDetails(existingEmployee);
                    System.out.println("Generated token: " + token);

                    // Create a response with token and dashboard URL
                    Map<String, String> response = new HashMap<>();
                    response.put("jwtToken", token);
                    response.put("redirectUrl", "/employee/dashboard");

                    // Return the response with the token and redirect URL
                    return ResponseEntity.ok(response);
                } else {
                    // Invalid password
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Employee ID or password");
                }
            } else {
                // Employee not found
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Employee ID or password");
            }

        } catch (Exception e) {
            // Log any unexpected errors
            e.printStackTrace(); // Log the exception stack trace
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }

    
    // 2. View Personal Dialogue Sessions (Read-Only) and allow one-time comment editing
    @GetMapping("/dialoguesessions/{employeeId}")
    public ResponseEntity<List<DialogueSession>> getEmployeeSessions(@PathVariable String employeeId) {
        List<DialogueSession> sessions = service.getEmployeeSessions(employeeId);
        return ResponseEntity.ok(sessions);
    }

    // Endpoint to edit comments for the first meeting
    @PutMapping("/dialogue-sessions/{sessionId}/edit-comment")
    public ResponseEntity<String> editComment(@PathVariable String sessionId, @RequestBody String comment) {
        boolean updated = service.editSessionComment(sessionId, comment);
        if (updated) {
            return ResponseEntity.ok("Comment updated successfully!");
        } else {
            return ResponseEntity.status(403).body("Comment cannot be edited more than once.");
        }
    }

    // 3. Upload Documents - both requested by Super Admin and any other documents
//    @PostMapping("/documents/upload")
//    public ResponseEntity<My_Document> uploadDocument(@RequestBody My_Document document) {
//        // Retrieve authenticated employee details from SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof EmployeeDetails) {
//            EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
//
//            // Automatically set employee ID and name in the document
//            document.setEmployeeId(employeeDetails.getEmployeeId());
//            document.setEmployeeName(employeeDetails.getEmployeeName());
//
//            // Proceed to upload and save the document
//            My_Document savedDocument = documentService.uploadDocument(document);
//            return ResponseEntity.ok(savedDocument);
//        } else {
//            // If the user is not authenticated, return an unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
    
    @PostMapping("/upload")
    public ResponseEntity<My_Document> uploadDocument(@RequestBody My_Document document,
                                                      @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the Bearer prefix
            String jwtToken = token.substring(7);

            // Extract employee details from the token
            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);
            String employeeName = jwtTokenUtil.getEmployeeNameFromToken(jwtToken);

            if (employeeId != null && employeeName != null) {
                // Automatically set employee ID and name in the document
                document.setEmployeeId(employeeId);
                document.setEmployeeName(employeeName);

                // Proceed to upload and save the document
                My_Document savedDocument = documentService.uploadDocument(document);
                return ResponseEntity.ok(savedDocument);
            } else {
                // If employee details cannot be extracted, return unauthorized response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            // Handle any unexpected errors during document upload
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 4. Submit Leave Request for Super Admin approval
//    @PostMapping("/leaverequests/submit")
//    public ResponseEntity<TimeOffRequest> submitLeaveRequest(@RequestBody TimeOffRequest leaveRequest) {
//        // Retrieve authenticated employee details from SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof EmployeeDetails) {
//            EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
//
//            // Automatically set employee ID and name in the leave request
//            leaveRequest.setEmployeeId(employeeDetails.getEmployeeId());
//           //leaveRequest.setEmployeeName(employeeDetails.getEmployeeName());
//
//            // Submit the leave request with employee details
//            TimeOffRequest submittedRequest = timeOffRequestService.submitLeaveRequest(leaveRequest);
//            return ResponseEntity.ok(submittedRequest);
//        } else {
//            // If the user is not authenticated, return an unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }


    @PostMapping("/leave/submit")
    public ResponseEntity<TimeOffRequest> submitLeaveRequest(@RequestBody TimeOffRequest leaveRequest,
                                                             @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the Bearer prefix
            String jwtToken = token.substring(7);

            // Extract employee details from the token
            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);
           // String employeeName = jwtTokenUtil.getEmployeeNameFromToken(jwtToken);

            if (employeeId != null) {
                // Automatically set employee ID and name in the leave request
                leaveRequest.setEmployeeId(employeeId);
               // leaveRequest.setEmployeeName(employeeName);

                // Submit the leave request with employee details
                TimeOffRequest submittedRequest = timeOffRequestService.submitLeaveRequest(leaveRequest);
                return ResponseEntity.ok(submittedRequest);
            } else {
                // If employee details cannot be extracted, return unauthorized response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

        } catch (Exception e) {
            // Handle any unexpected errors during leave request submission
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    // 5. Submit Timesheet for Super Admin approval
//    @PostMapping("/timesheets/submit")
//    public ResponseEntity<Timesheet> submitTimesheet(@RequestBody Timesheet timesheet) {
//        // Retrieve authenticated employee details from the SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof EmployeeDetails) {
//            EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
//            
//            // Automatically set employee ID and name on the timesheet
//            timesheet.setEmployeeId(employeeDetails.getEmployeeId());
//            timesheet.setEmployeeName(employeeDetails.getEmployeeName());
//            
//            // Set other timesheet details like status and attendance
//            timesheet.calculateHours(); // Calculate working hours
//            timesheet.setStatus("Pending");
//            timesheet.setAttendanceStatus("Regularized");
//
//            // Save the timesheet
//            Timesheet submittedTimesheet = timesheetService.submitTimesheet(timesheet);
//            return ResponseEntity.ok(submittedTimesheet);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    
//    @PostMapping("/timesheets/submit")
//    public ResponseEntity<Timesheet> submitTimesheet(@RequestBody Timesheet timesheet, 
//                                                     @RequestHeader("Authorization") String token) {
//        try {
//            // Extract the token without the "Bearer" prefix
//            String jwtToken = token.substring(7);
//
//            // Extract employee details from the token
//            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);
//            String employeeName = jwtTokenUtil.getEmployeeNameFromToken(jwtToken);
//
//            if (employeeId != null && employeeName != null) {
//                // Automatically set employee ID and name on the timesheet
//                timesheet.setEmployeeId(employeeId);
//                timesheet.setEmployeeName(employeeName);
//
//                // Calculate hours and set the status based on the timesheet details
//                timesheet.calculateHours();  // Make sure this method works correctly
//                timesheet.setStatus("Pending");  // Set default status as pending
//                timesheet.setAttendanceStatus("Regularized");  // Set default attendance status
//
//                // Log the timesheet before saving
//                System.out.println("Submitting Timesheet: " + timesheet);
//
//                // Save the timesheet to the database
//                Timesheet submittedTimesheet = timesheetService.submitTimesheet(timesheet);
//                System.out.println("Timesheet submitted successfully: " + submittedTimesheet);
//                return ResponseEntity.ok(submittedTimesheet);
//            } else {
//                // Return unauthorized if employee details cannot be extracted
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//            }
//        } catch (Exception e) {
//            // Handle any unexpected errors during timesheet submission
//            e.printStackTrace(); // Log the error for debugging
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
    
    @PostMapping("/timesheets/submit")
    public ResponseEntity<Timesheet> submitTimesheet(@RequestBody Timesheet timesheet, 
                                                     @RequestHeader("Authorization") String token) {
        try {
            System.out.println("Received token: " + token);

            // Extract the token without the "Bearer " prefix
            String jwtToken = token.substring(7);

            // Extract employee details from the token
            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);
            String employeeName = jwtTokenUtil.getEmployeeNameFromToken(jwtToken);

            if (employeeId != null && employeeName != null) {
                // Automatically set employee ID and name on the timesheet
                timesheet.setEmployeeId(employeeId);
                timesheet.setEmployeeName(employeeName);

                // Calculate hours and set the status based on the timesheet details
                timesheet.calculateHours();  // Make sure this method works correctly
                timesheet.setStatus("Pending");  // Set default status as pending
                timesheet.setAttendanceStatus("Regularized");  // Set default attendance status

                // Log the timesheet before saving
                System.out.println("Submitting Timesheet: " + timesheet);

                // Save the timesheet to the database
                Timesheet submittedTimesheet = timesheetService.submitTimesheet(timesheet);
                System.out.println("Timesheet submitted successfully: " + submittedTimesheet);
                return ResponseEntity.ok(submittedTimesheet);
            } else {
                // Return unauthorized if employee details cannot be extracted
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            // Handle any unexpected errors during timesheet submission
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // 6. Submit Expense for Super Admin approval
    
//    @PostMapping("/expenses/submit")
//    public ResponseEntity<Expense> submitExpense(@RequestBody Expense expense) {
//        // Retrieve authenticated employee details from SecurityContext
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof EmployeeDetails) {
//            EmployeeDetails employeeDetails = (EmployeeDetails) authentication.getPrincipal();
//
//            // Automatically set employee ID and name in the expense
//            expense.setEmployeeId(employeeDetails.getEmployeeId());
//            expense.setEmployeeName(employeeDetails.getEmployeeName());
//
//            // Submit the expense with employee details
//            Expense submittedExpense = expenseService.submitExpense(expense);
//            return ResponseEntity.ok(submittedExpense);
//        } else {
//            // If the user is not authenticated, return an unauthorized response
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }

    
    
    @PostMapping("/expenses/submit")
    public ResponseEntity<Expense> submitExpense(@RequestBody Expense expense,
                                                 @RequestHeader("Authorization") String token) {
        try {
            // Extract the token without the "Bearer" prefix
            String jwtToken = token.substring(7);

            // Extract employee details from the JWT token
            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);
            String employeeName = jwtTokenUtil.getEmployeeNameFromToken(jwtToken);

            if (employeeId != null && employeeName != null) {
                // Automatically set employee ID and name in the expense
                expense.setEmployeeId(employeeId);
                expense.setEmployeeName(employeeName);

                // Submit the expense with employee details
                Expense submittedExpense = expenseService.submitExpense(expense);
                return ResponseEntity.ok(submittedExpense);
            } else {
                // Return unauthorized if employee details cannot be extracted
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            // Handle any unexpected errors during expense submission
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //  View Payslips in PDF format
    
    @GetMapping("/payslips")
    public ResponseEntity<byte[]> viewSalarySlip(@RequestParam String employeeId) {
        // Fetch the payslips from the database
        List<Payslip> payslips = payslipService.findByEmployeeId(employeeId);
        if (payslips == null || payslips.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Assuming you want to return the first payslip's file
        Payslip payslip = payslips.get(0);
        Path filePath = Paths.get(payslip.getFilePath());
        
        try {
            byte[] fileContent = Files.readAllBytes(filePath);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + filePath.getFileName().toString() + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //   View Perks added by Employee
    
    @GetMapping("/perks")
    public ResponseEntity<List<Perk>> viewPerks() {
        List<Perk> perks = perkService.getAllPerks();
        return ResponseEntity.ok(perks);
    }

    //  View Company Policy added by Employee
    
    @GetMapping("/companypolicy")
    public ResponseEntity<String> viewCompanyPolicy() {
        String policy = companyPolicyService.getCompanyPolicy();
        return ResponseEntity.ok(policy);
    }
}
