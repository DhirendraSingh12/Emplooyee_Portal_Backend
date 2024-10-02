package com.example.th.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.th.model.Admin;
import com.example.th.model.Payslip;
import com.example.th.model.SalarySlipUploadRequest;
import com.example.th.repository.AdminRepository;
import com.example.th.service.PayslipService;
import com.example.th.utils.JwtTokenUtil;

import io.jsonwebtoken.io.IOException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private AdminRepository adminRepository;
//
    @Autowired
    private PasswordEncoder passwordEncoder;
    
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    

	private final PayslipService payslipService;

	public AdminController(PayslipService payslipService) {
		this.payslipService = payslipService;
	}
	

//	@PostMapping("/login")
//    public String login(@RequestBody Admin admin, Model model) {
//        try {
//            // Create an authentication token using provided username and password
//            UsernamePasswordAuthenticationToken authToken =
//                    new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword());
//
//            // Authenticate the token
//            Authentication authentication = authenticationManager.authenticate(authToken);
//
//            // Set the authentication in the SecurityContext
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Redirect to the admin dashboard upon successful login
//            return "redirect:/admin/dashboard";
//
//        } catch (Exception e) {
//            // If authentication fails, return to login page with error message
//            model.addAttribute("error", "Invalid username or password");
//            return "admin-login";  // Your login page with error handling
//        }
//    }
//	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Admin admin) {
	    // Fetch the admin from the repository
	    Optional<Admin> existingAdminOptional = adminRepository.findByUsername(admin.getUsername());

	    // Validate username and password
	    if (existingAdminOptional.isPresent()) {
	        Admin existingAdmin = existingAdminOptional.get();
	        // Use password encoder to match the password
	        if (passwordEncoder.matches(admin.getPassword(), existingAdmin.getPassword())) {
	            // Create claims and add the role
	            Map<String, Object> claims = new HashMap<>();
	            claims.put("role", "Admin");

	            // Generate JWT token with claims and username
	            String token = jwtTokenUtil.generateToken(existingAdmin.getUsername());

	            // Create a response with token and dashboard URL
	            Map<String, String> response = new HashMap<>();
	            response.put("jwtToken", token);
	            response.put("redirectUrl", "/admin/dashboard");

	            // Return the response with the token and redirect URL
	            return ResponseEntity.ok(response);
	        }
	    }

	    // If authentication fails
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}

//    @GetMapping("/dashboard")
//    public ResponseEntity<String> dashboard() {
//        return ResponseEntity.ok("Welcome to the Admin Dashboard!");
//    }

	// Salary Slips

//	@PostMapping("/salary-slips")
//	public ResponseEntity<Payslip> createSalarySlip(@RequestBody Payslip payslip) {
//		Payslip createdSlip = payslipService.createSalarySlip(payslip);
//		return new ResponseEntity<>(createdSlip, HttpStatus.CREATED);
//	}
	
	@PostMapping("/salaryupload")
    public ResponseEntity<String> uploadSalarySlips(@RequestBody SalarySlipUploadRequest request) {
        // Create directory path dynamically
        String directoryPath = String.format("uploads/%d/%d/", request.getYear(), request.getMonth());
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Define the file path
        Path path = Paths.get(directoryPath + request.getEmployeeId() + ".pdf");

        try {
            // Decode base64 file content
            byte[] decodedBytes = java.util.Base64.getDecoder().decode(request.getFileBase64());
            try {
				Files.write(path, decodedBytes);
			} catch (java.io.IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            // Process the file (e.g., save metadata to the database)
            Payslip payslip = new Payslip();
            payslip.setEmployeeId(request.getEmployeeId());
            payslip.setFilePath(path.toString());
            payslipService.savePayslip(payslip);

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	

	@GetMapping("/salaryslips")
	public ResponseEntity<List<Payslip>> getAllSalarySlips() {
		List<Payslip> slips = payslipService.getAllSalarySlips();
		return new ResponseEntity<>(slips, HttpStatus.OK);
	}

}
