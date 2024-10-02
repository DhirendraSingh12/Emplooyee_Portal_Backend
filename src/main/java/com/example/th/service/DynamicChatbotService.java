//package com.example.th.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.model.CompanyPolicy;
//import com.example.th.model.Employee;
//import com.example.th.model.My_Document;
//import com.example.th.model.Perk;
//import com.example.th.repository.CompanyPolicyRepository;
//import com.example.th.repository.DocumentRepository;
//import com.example.th.repository.EmployeeRepository;
//import com.example.th.repository.PerkRepository;
//
//@Service
//public class DynamicChatbotService {
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private CompanyPolicyRepository companyPolicyRepository;
//
//    @Autowired
//    private PerkRepository perkRepository;
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    // Greet the employee
//    public String greetEmployee() {
//        return "Hello! How can I help you?";
//    }
//
//    // Handle the user's query based on employeeId
//    public String handleQuery(String query, String employeeId) {
//        // Convert query to lowercase for easier matching
//        query = query.toLowerCase();
//
//        // Check for keywords and route to the appropriate repository
//        if (query.contains("details")) {
//            return getEmployeeDetails(employeeId);
//        } else if (query.contains("policy") || query.contains("policies")) {
//            return getCompanyPolicies();
//        } else if (query.contains("perks")) {
//            return getPerks();
//        } else if (query.contains("documents")) {
//            return getDocuments(employeeId);
//        } else {
//            return "Sorry, I couldn't understand your question. Please try again!";
//        }
//    }
//
//    // Fetch employee details based on employeeId
//    private String getEmployeeDetails(String employeeId) {
//        Employee employee = employeeRepository.findById(employeeId).orElse(null);
//        if (employee != null) {
//            return "Employee Name: " + employee.getEmployeeName() + ", Email: " + employee.getEmail();
//        }
//        return "Employee not found!";
//    }
//
//    // Fetch all company policies
//    private String getCompanyPolicies() {
//        List<CompanyPolicy> policies = companyPolicyRepository.findAll();
//        StringBuilder response = new StringBuilder("Company Policies:\n");
//        for (CompanyPolicy policy : policies) {
//            response.append(policy.getFileName()).append(": ").append(policy.getDescription()).append("\n");
//        }
//        return response.toString();
//    }
//
//    // Fetch all perks
//    private String getPerks() {
//        List<Perk> perks = perkRepository.findAll();
//        StringBuilder response = new StringBuilder("Available Perks:\n");
//        for (Perk perk : perks) {
//            response.append(perk.getPerkName()).append(": ").append(perk.getDescription()).append("\n");
//        }
//        return response.toString();
//    }
//
//    // Fetch documents for the employee
//    private String getDocuments(String employeeId) {
//        List<My_Document> documents = documentRepository.findByEmployeeId(employeeId);
//        if (documents.isEmpty()) {
//            return "No documents found for employee ID: " + employeeId;
//        }
//        StringBuilder response = new StringBuilder("Your Documents:\n");
//        for (My_Document doc : documents) {
//            response.append(doc.getDocumentType()).append(": Uploaded on ").append(doc.getUploadedDate()).append("\n");
//        }
//        return response.toString();
//    }
//}


package com.example.th.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.th.model.CompanyPolicy;
import com.example.th.model.Employee;
import com.example.th.model.My_Document;
import com.example.th.model.Perk;
import com.example.th.repository.CompanyPolicyRepository;
import com.example.th.repository.DocumentRepository;
import com.example.th.repository.EmployeeRepository;
import com.example.th.repository.PerkRepository;
import com.example.th.utils.JwtTokenUtil;

@Service
public class DynamicChatbotService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompanyPolicyRepository companyPolicyRepository;

    @Autowired
    private PerkRepository perkRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // Greet the employee
    public String greetEmployee() {
        return "Hello! How can I help you?";
    }

    // Handle the user's query based on JWT token
    public String handleQuery(String query, String jwtToken) {
        // Validate the token and extract employee ID
        if (jwtTokenUtil.validateToken(jwtToken, userDetailsService.loadUserByUsername(jwtTokenUtil.getEmployeeIdFromToken(jwtToken)))) {
            String employeeId = jwtTokenUtil.getEmployeeIdFromToken(jwtToken);

            // Convert query to lowercase for easier matching
            query = query.toLowerCase();

            // Check for keywords and route to the appropriate repository
            if (query.contains("details")) {
                return getEmployeeDetails(employeeId);
            } else if (query.contains("policy") || query.contains("policies")) {
                return getCompanyPolicies();
            } else if (query.contains("perks")) {
                return getPerks();
            } else if (query.contains("documents")) {
                return getDocuments(employeeId);
            } else {
                return "Sorry, I couldn't understand your question. Please try again!";
            }
        } else {
            return "Invalid token. Please log in again.";
        }
    }

    // Fetch employee details based on employeeId
    private String getEmployeeDetails(String employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            return "Employee Name: " + employee.getEmployeeName() + ", Email: " + employee.getEmail();
        }
        return "Employee not found!";
    }

    // Fetch all company policies
    private String getCompanyPolicies() {
        List<CompanyPolicy> policies = companyPolicyRepository.findAll();
        StringBuilder response = new StringBuilder("Company Policies:\n");
        for (CompanyPolicy policy : policies) {
            response.append(policy.getFileName()).append(": ").append(policy.getDescription()).append("\n");
        }
        return response.toString();
    }

    // Fetch all perks
    private String getPerks() {
        List<Perk> perks = perkRepository.findAll();
        StringBuilder response = new StringBuilder("Available Perks:\n");
        for (Perk perk : perks) {
            response.append(perk.getPerkName()).append(": ").append(perk.getDescription()).append("\n");
        }
        return response.toString();
    }

    // Fetch documents for the employee
    private String getDocuments(String employeeId) {
        List<My_Document> documents = documentRepository.findByEmployeeId(employeeId);
        if (documents.isEmpty()) {
            return "No documents found for employee ID: " + employeeId;
        }
        StringBuilder response = new StringBuilder("Your Documents:\n");
        for (My_Document doc : documents) {
            response.append(doc.getDocumentType()).append(": Uploaded on ").append(doc.getUploadedDate()).append("\n");
        }
        return response.toString();
    }
}

