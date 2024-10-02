package com.example.th.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.th.DTO.EmployeeDTO;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Asset_Managenment;
import com.example.th.model.CompanyPolicy;
import com.example.th.model.DialogueSession;
import com.example.th.model.Employee;
import com.example.th.model.Expense;
import com.example.th.model.My_Document;
import com.example.th.model.Perk;
import com.example.th.model.SuperAdmin;
import com.example.th.model.TimeOffRequest;
import com.example.th.model.Timesheet;
import com.example.th.repository.SuperAdminRepository;
import com.example.th.service.AssetService;
import com.example.th.service.CompanyPolicyService;
import com.example.th.service.DialogueSessionService;
import com.example.th.service.DocumentService;
import com.example.th.service.EmployeeService;
import com.example.th.service.ExpenseService;
import com.example.th.service.PerkService;
import com.example.th.service.TimeOffRequestService;
import com.example.th.service.TimesheetService;
import com.example.th.utils.JwtTokenUtil;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/superadmin")
public class SuperAdminController {
	
//	@Value("${superadmin.default.username}")
//    private String superadminUsername;
//
//    @Value("${superadmin.default.password}")
//    private String superadminPassword;
    
    @Autowired
    private SuperAdminRepository superAdminRepository;
//    
     @Autowired
      private PasswordEncoder passwordEncoder;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
   
//    @Autowired
//    private AuthenticationManager authenticationManager;
   
	private final EmployeeService employeeService;
	private final AssetService assetService;
	private final DocumentService documentService;
	private final PerkService perkService;
	private final CompanyPolicyService companypolicyService;
	private final TimeOffRequestService timeOffRequestService;
	private final TimesheetService timesheetService;
	private final ExpenseService expenseService;
	private final DialogueSessionService service;
	
//	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Adjust pattern as needed

	public SuperAdminController(EmployeeService employeeService, AssetService assetService,
			DocumentService documentService, PerkService perkService, CompanyPolicyService companypolicyService,
			TimeOffRequestService timeOffRequestService, TimesheetService timesheetService,
			ExpenseService expenseService, DialogueSessionService service) {
		this.employeeService = employeeService;
		this.assetService = assetService;
		this.documentService = documentService;
		this.perkService = perkService;
		this.companypolicyService = companypolicyService;
		this.timeOffRequestService = timeOffRequestService;
		this.timesheetService = timesheetService;
		this.expenseService = expenseService;
		this.service = service;
	}

	
//	@PostMapping("/login")
//	//@RequestMapping(name=)
//    public String login(@RequestBody SuperAdmin user,
//                        Model model) {
//        try {
//            // Create an authentication token using provided username and password
//            UsernamePasswordAuthenticationToken authToken = 
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
//
//            // Authenticate the token
//            Authentication authentication = authenticationManager.authenticate(authToken);
//
//            // Set the authentication in the SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Redirect to the superadmin dashboard upon successful login
//            return "redirect:/superadmin/dashboard";
//
//        } catch (Exception e) {
//            // If authentication fails, return to login page with error message
//            model.addAttribute("error", "Invalid username or password");
//            return "superadmin-login";  // Your login page with error handling
//        }
//    }
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody SuperAdmin user) {
	    Optional<SuperAdmin> optionalSuperAdmin = superAdminRepository.findByUsername(user.getUsername());

	    if (optionalSuperAdmin.isPresent()) {
	        SuperAdmin superAdmin = optionalSuperAdmin.get();
	        
	        // Use password encoder to match the password
	        if (passwordEncoder.matches(user.getPassword(), superAdmin.getPassword())) {
	            // Pass an empty map for claims and the username
	            Map<String, Object> claims = new HashMap<>();
	            claims.put("role", "SuperAdmin"); // Optionally add role to claims
	            String token = jwtTokenUtil.generateToken(superAdmin.getUsername());

	            Map<String, String> response = new HashMap<>();
	            response.put("jwtToken", token);
	            response.put("redirectUrl", "/superadmin/dashboard");

	            return ResponseEntity.ok(response);
	        }
	    }

	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}

	// Employee Management

	@PostMapping("/addemployee")
	public ResponseEntity<Employee> createEmployee(
	    @ModelAttribute EmployeeDTO employeeDTO) {

	    // Convert DTO to Employee entity
	    Employee employee = new Employee();
	    employee.setEmployeeName(employeeDTO.getEmployeeName());
	    employee.setGender(employeeDTO.getGender());
	    employee.setMobile(employeeDTO.getMobile());
	   // employee.setRawPassword(employeeDTO.getPassword());
	    employee.setPassword(employeeDTO.getPassword());
	    employee.setDesignation(employeeDTO.getDesignation());
	    employee.setDepartment(employeeDTO.getDepartment());
	    employee.setAddress(employeeDTO.getAddress());
	    employee.setEmail(employeeDTO.getEmail());

//	    // Convert String DOB to LocalDate
//	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	    if (employeeDTO.getDob() != null) {
//	        LocalDate localDateDob = LocalDate.parse(employeeDTO.getDob(), formatter);
//	        employee.setDob(localDateDob);
//	    }
	 // Convert string date of birth to LocalDate
	    String dobString = employeeDTO.getDob(); // Example: "1998-05-05"
	    if (dobString != null && !dobString.isEmpty()) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate localDateDob = LocalDate.parse(dobString, formatter);
	        employee.setDob(localDateDob);
	    }

	    employee.setEducation(employeeDTO.getEducation());

	    // Handle file upload
	    String photoUrl = null;
	    if (employeeDTO.getPhoto() != null && !employeeDTO.getPhoto().isEmpty()) {
	        photoUrl = handleFileUpload(employeeDTO.getPhoto());
	    }
	    employee.setPhoto(photoUrl);

	    // Save employee in MongoDB
	    Employee createdEmployee = employeeService.createEmployee(employee);
	    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}


	private String handleFileUpload(MultipartFile file) {
	    // Use your custom upload directory
	    String uploadDir = "C:\\Users\\ASUS\\Pictures"; // Define your upload directory
	    
	    try {
	        // Create the directory if it doesn't exist
	        File dir = new File(uploadDir);
	        if (!dir.exists()) {
	            dir.mkdirs(); // Create directories if necessary
	        }

	        // Construct the destination file path
	        String fileName = file.getOriginalFilename();
	        File destinationFile = new File(dir, fileName);

	        // Transfer the file to the destination
	        file.transferTo(destinationFile);

	        // Return the relative file path (for web access, if applicable)
	        return uploadDir + "\\" + fileName; // Use backslashes for Windows path
	    } catch (IOException e) {
	        throw new RuntimeException("Failed to store file", e);
	    }
	}

	
	@PostMapping("/bulkemployee")
    public ResponseEntity<List<Employee>> addEmployeesInBulk(@RequestBody List<Employee> employees) {
        List<Employee> savedEmployees = employeeService.addEmployeesInBulk(employees);
        return ResponseEntity.ok(savedEmployees);
    }
	
//	@GetMapping("/allemployees")
//    public ResponseEntity<List<Employee>> getAllEmployees() {
//        // Logic to return all employees
//        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
//    }
	
	@GetMapping("/allemployees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
	    List<Employee> employees = employeeService.getAllEmployees();
	    
	    if (employees.isEmpty()) {
	        // Return a 204 No Content if the list is empty
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    
	    return new ResponseEntity<>(employees, HttpStatus.OK);
	}


//	@GetMapping("/allemployees")
//	public ResponseEntity<List<Employee>> getAllEmployees() {
//		List<Employee> employees = employeeService.getAllEmployees();
//		return new ResponseEntity<>(employees, HttpStatus.OK);
//	}

	@GetMapping("/getemployees/{employeeId}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		return employee != null ? new ResponseEntity<>(employee, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

//	@PutMapping(value="/editemployees/{employeeId}", consumes = "multipart/form-data")
//	public ResponseEntity<Employee> updateEmployee(@PathVariable String employeeId, @RequestBody Employee employee) {
//	    // Call service method to update the employee
//	    Employee updatedEmployee = employeeService.updateEmployee(employeeId, employee);
//	    
//	    // Check if the update was successful
//	    return updatedEmployee != null 
//	        ? new ResponseEntity<>(updatedEmployee, HttpStatus.OK) 
//	        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
	
//	@PutMapping(value = "/editemployees/{employeeId}", consumes = "application/json")
//	public ResponseEntity<Employee> updateEmployee(
//	        @PathVariable String employeeId,
//	        @RequestBody EmployeeDTO employeeUpdateDTO) {
//
//	    // Create an Employee object to hold the updated data
//	    Employee updatedEmployee = new Employee();
//	    updatedEmployee.setEmployeeName(employeeUpdateDTO.getEmployeeName());
//	    updatedEmployee.setGender(employeeUpdateDTO.getGender());
//	    updatedEmployee.setMobile(employeeUpdateDTO.getMobile());
//	   // updatedEmployee.setPassword(employeeUpdateDTO.getPassword());
//	    updatedEmployee.setDesignation(employeeUpdateDTO.getDesignation());
//	    updatedEmployee.setDepartment(employeeUpdateDTO.getDepartment());
//	    updatedEmployee.setAddress(employeeUpdateDTO.getAddress());
//	    updatedEmployee.setEmail(employeeUpdateDTO.getEmail());
//
//	    // Convert the String date to LocalDate
//	    try {
//	        String dobString = employeeUpdateDTO.getDob();
//	        if (dobString != null && !dobString.isEmpty()) {
//	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	            LocalDate dob = LocalDate.parse(dobString, formatter);
//	            updatedEmployee.setDob(dob);
//	        }
//	    } catch (DateTimeParseException e) {
//	        // Handle parsing error (e.g., log the error, return a bad request)
//	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	    }
//	    updatedEmployee.setEducation(employeeUpdateDTO.getEducation());
//
//	    // Call service method to update the employee
//	    Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);
//
//	    // Check if the update was successful
//	    return result != null 
//	        ? new ResponseEntity<>(result, HttpStatus.OK) 
//	        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
	
	@PutMapping(value = "/editemployees/{employeeId}", consumes = "application/json")
	public ResponseEntity<Employee> updateEmployee(
	        @PathVariable String employeeId,
	        @RequestBody EmployeeDTO employeeUpdateDTO) {

	    if (employeeUpdateDTO == null) {
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }

	    Employee updatedEmployee = new Employee();
	    updatedEmployee.setEmployeeName(employeeUpdateDTO.getEmployeeName());
	    updatedEmployee.setGender(employeeUpdateDTO.getGender());
	    updatedEmployee.setMobile(employeeUpdateDTO.getMobile());
	    updatedEmployee.setDesignation(employeeUpdateDTO.getDesignation());
	    updatedEmployee.setDepartment(employeeUpdateDTO.getDepartment());
	    updatedEmployee.setAddress(employeeUpdateDTO.getAddress());
	    updatedEmployee.setEmail(employeeUpdateDTO.getEmail());

	    try {
	        String dobString = employeeUpdateDTO.getDob();
	        if (dobString != null && !dobString.isEmpty()) {
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	            LocalDate dob = LocalDate.parse(dobString, formatter);
	            updatedEmployee.setDob(dob);
	        }
	    } catch (DateTimeParseException e) {
	        // Log the parsing error for better visibility
	        System.err.println("Date parsing error: " + e.getMessage());
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	    updatedEmployee.setEducation(employeeUpdateDTO.getEducation());

	    Employee result = employeeService.updateEmployee(employeeId, updatedEmployee);

	    return result != null 
	        ? new ResponseEntity<>(result, HttpStatus.OK) 
	        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}


	@DeleteMapping("/deleteemployees/{employeeId}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
	    boolean isDeleted = employeeService.deleteEmployee(employeeId);

	    if (isDeleted) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successful deletion
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Employee not found
	    }
	}



//	@DeleteMapping("/deleteemployees/{employeeId}")
//	public ResponseEntity<Void> deleteEmployee(@PathVariable String employeeId) {
//		employeeService.deleteEmployee(employeeId);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}

	// Asset Management

	@PostMapping("/addassets")
	public ResponseEntity<Asset_Managenment> addAsset(@RequestBody Asset_Managenment asset) {
		Asset_Managenment createdAsset = assetService.addAsset(asset);
		return new ResponseEntity<>(createdAsset, HttpStatus.CREATED);
	}

	@GetMapping("/allassets")
	public ResponseEntity<List<Asset_Managenment>> getAllAssets() {
		List<Asset_Managenment> assets = assetService.getAllAssets();
		return new ResponseEntity<>(assets, HttpStatus.OK);
	}

//	@PutMapping("/editassets/{employeeId}")
//	public ResponseEntity<Asset_Managenment> updateAsset(@PathVariable String employeeId, @RequestBody Asset_Managenment asset) {
//		Asset_Managenment updatedAsset = assetService.updateAsset(employeeId, asset);
//		return updatedAsset != null ? new ResponseEntity<>(updatedAsset, HttpStatus.OK)
//				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
	
	@PutMapping("/editassets/{assetId}")
	public ResponseEntity<Asset_Managenment> updateAsset(@PathVariable String assetId, @RequestBody Asset_Managenment asset) {
	    try {
	        // Call the service to update the asset based on employeeId
	        Asset_Managenment updatedAsset = assetService.updateAsset(assetId, asset);
	        return new ResponseEntity<>(updatedAsset, HttpStatus.OK);
	    } catch (ResourceNotFoundException e) {
	        // If the asset is not found, return a 404 Not Found response
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    } catch (Exception e) {
	        // For any other unexpected error, return a 500 Internal Server Error response
	        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@DeleteMapping("/deleteassets/{assetId}")
	public ResponseEntity<Void> deleteAsset(@PathVariable String assetId) {
	    try {
	        assetService.deleteAsset(assetId);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (ResourceNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	// DialogueSession Management

	@PostMapping("/createsession")
	public ResponseEntity<DialogueSession> createSession(@RequestBody DialogueSession session) {
		DialogueSession savedSession = service.createSession(session);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSession);
	}
	
	@GetMapping("/allsession")
    public ResponseEntity<List<DialogueSession>> getAllSessions() {
        List<DialogueSession> sessions = service.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<List<DialogueSession>> getSessionsForEmployee(@PathVariable String employeeId) {
		List<DialogueSession> sessions = service.getSessionsForEmployee(employeeId);
		return ResponseEntity.ok(sessions);
	}

   @PutMapping("/editsession/{sessionId}")
	public ResponseEntity<DialogueSession> updateSession(@PathVariable String sessionId,
			@RequestBody DialogueSession session) {
		DialogueSession updatedSession = service.updateSession(sessionId, session);
		if (updatedSession != null) {
			return ResponseEntity.ok(updatedSession);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	


	@DeleteMapping("/deletesession/{sessionId}")
	public ResponseEntity<Void> deleteSession(@PathVariable String sessionId) {
		boolean isDeleted = service.deleteSession(sessionId);
		if (isDeleted) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	// Document Management

	@PostMapping("/adddocuments")
	public ResponseEntity<My_Document> createDocument(@RequestBody My_Document document) {
		My_Document createdDocument = documentService.createDocument(document);
		return new ResponseEntity<>(createdDocument, HttpStatus.CREATED);
	}

	@GetMapping("/alldocuments")
	public ResponseEntity<List<My_Document>> getAllDocuments() {
		List<My_Document> documents = documentService.getAllDocuments();
		return new ResponseEntity<>(documents, HttpStatus.OK);
	}
	
	@PutMapping("/approveDocument/{documentId}")
	public ResponseEntity<String> approveDocument(@PathVariable String documentId) {
	    try {
	        // Call service method to approve the document
	        boolean isApproved = documentService.approveDocument(documentId);
	        
	        if (isApproved) {
	            return new ResponseEntity<>("Document approved successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Document not found or already approved", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while approving the document", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/rejectDocument/{documentId}")
	public ResponseEntity<String> rejectDocument(@PathVariable String documentId) {
	    try {
	        // Call service method to reject the document
	        boolean isRejected = documentService.rejectDocument(documentId);
	        
	        if (isRejected) {
	            return new ResponseEntity<>("Document rejected successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Document not found or already rejected", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while rejecting the document", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@DeleteMapping("/deleteDocument/{documentId}")
	public ResponseEntity<String> deleteDocument(@PathVariable String documentId) {
	    try {
	        // Call service method to delete the document
	        boolean isDeleted = documentService.deleteDocument(documentId);
	        
	        if (isDeleted) {
	            return new ResponseEntity<>("Document deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while deleting the document", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Perks Management

	@PostMapping("/addperks")
	public ResponseEntity<Perk> addPerk(@RequestBody Perk perk) {
		Perk createdPerk = perkService.createPerk(perk);
		return new ResponseEntity<>(createdPerk, HttpStatus.CREATED);
	}

	@GetMapping("/allperks")
	public ResponseEntity<List<Perk>> getAllPerks() {
		List<Perk> perks = perkService.getAllPerks();
		return new ResponseEntity<>(perks, HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePerk/{perkId}")
	public ResponseEntity<String> deletePerk(@PathVariable String perkId) {
	    try {
	        // Call service method to delete the perk
	        boolean isDeleted = perkService.deletePerk(perkId);
	        
	        if (isDeleted) {
	            return new ResponseEntity<>("Perk deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Perk not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while deleting the perk", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Company Policies

	@PostMapping("/addpolicies")
	public ResponseEntity<CompanyPolicy> addPolicy(@RequestBody CompanyPolicy policy) {
		CompanyPolicy createdPolicy = companypolicyService.createPolicy(policy);
		return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
	}

	@GetMapping("/allpolicies")
	public ResponseEntity<List<CompanyPolicy>> getAllPolicies() {
		List<CompanyPolicy> policies = companypolicyService.getAllPolicies();
		return new ResponseEntity<>(policies, HttpStatus.OK);
	}
	
	@DeleteMapping("/deletePolicy/{policyId}")
	public ResponseEntity<String> deletePolicy(@PathVariable String policyId) {
	    try {
	        // Call service method to delete the policy
	        boolean isDeleted = companypolicyService.deletePolicy(policyId);
	        
	        if (isDeleted) {
	            return new ResponseEntity<>("Policy deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Policy not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while deleting the policy", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Time-Off Requests

	@PutMapping("/approvetimeoff/{leaveId}")
	public ResponseEntity<String> approveTimeOffRequest(@PathVariable String leaveId) {
	    boolean isApproved = timeOffRequestService.approveTimeOffRequest(leaveId) != null;
	    
	    if (isApproved) {
	        return new ResponseEntity<>("Time-off request approved successfully.", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Time-off request not found or approval failed.", HttpStatus.NOT_FOUND);
	    }
	}
	
	@PutMapping("/rejecttimeoff/{leaveId}")
	public ResponseEntity<String> rejectTimeOffRequest(@PathVariable String leaveId) {
	    boolean isRejected = timeOffRequestService.rejectTimeOffRequest(leaveId) != null;
	    
	    if (isRejected) {
	        return new ResponseEntity<>("Time-off request rejected successfully.", HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>("Time-off request not found or rejection failed.", HttpStatus.NOT_FOUND);
	    }
	}

	@GetMapping("/alltimeoff")
	public ResponseEntity<List<TimeOffRequest>> getAllTimeOffRequests() {
		List<TimeOffRequest> requests = timeOffRequestService.getAllTimeOffRequests();
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}

	@GetMapping("/timeoff/{status}")
	public ResponseEntity<List<TimeOffRequest>> getTimeOffRequestsByStatus(@PathVariable String status) {
		List<TimeOffRequest> requests = timeOffRequestService.getTimeOffRequestsByStatus(status);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}

	// Timesheets

	@PutMapping("/approveTimesheet/{timesheetId}")
	public ResponseEntity<String> approveTimesheet(@PathVariable String timesheetId) {
	    try {
	        boolean isApproved = timesheetService.approveTimesheet(timesheetId);
	        
	        if (isApproved) {
	            return new ResponseEntity<>("Timesheet approved successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Timesheet not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while approving the timesheet", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/rejectTimesheet/{timesheetId}")
	public ResponseEntity<String> rejectTimesheet(@PathVariable String timesheetId) {
	    try {
	        boolean isRejected = timesheetService.rejectTimesheet(timesheetId);
	        
	        if (isRejected) {
	            return new ResponseEntity<>("Timesheet rejected successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Timesheet not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while rejecting the timesheet", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@GetMapping("/alltimesheets")
	public ResponseEntity<List<Timesheet>> getAllTimesheets() {
		List<Timesheet> timesheets = timesheetService.getAllTimesheets();
		return new ResponseEntity<>(timesheets, HttpStatus.OK);
	}

	@GetMapping("/timesheets/{employeeId}")
	public ResponseEntity<List<Timesheet>> getTimesheetForEmployee(@PathVariable String employeeId) {
		List<Timesheet> timesheets = timesheetService.getTimesheetsForEmployee(employeeId);
		return new ResponseEntity<>(timesheets, HttpStatus.OK);
	}

	// Expenses

	@PutMapping("/approveExpense/{expenseId}")
	public ResponseEntity<String> approveExpense(@PathVariable String expenseId) {
	    try {
	        boolean isApproved = expenseService.approveExpense(expenseId);
	        
	        if (isApproved) {
	            return new ResponseEntity<>("Expense approved successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while approving the expense", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/rejectExpense/{expenseId}")
	public ResponseEntity<String> rejectExpense(@PathVariable String expenseId) {
	    try {
	        boolean isRejected = expenseService.rejectExpense(expenseId);
	        
	        if (isRejected) {
	            return new ResponseEntity<>("Expense rejected successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Expense not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception e) {
	        return new ResponseEntity<>("An error occurred while rejecting the expense", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	@GetMapping("/allexpenses")
	public ResponseEntity<List<Expense>> getAllExpenses() {
		List<Expense> expenses = expenseService.getAllExpenses();
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}

	@GetMapping("/expenses/{status}")
	public ResponseEntity<List<Expense>> getExpensesByStatus(@PathVariable String status) {
		List<Expense> expenses = expenseService.getExpensesByStatus(status);
		return new ResponseEntity<>(expenses, HttpStatus.OK);
	}

}
