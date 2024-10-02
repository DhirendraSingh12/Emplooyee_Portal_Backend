//package com.example.th.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.model.CompanyPolicy;
//import com.example.th.repository.CompanyPolicyRepository;
//
//@Service
//public class CompanyPolicyService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private CompanyPolicyRepository companyPolicyRepository;
//
//    public CompanyPolicy createPolicy(CompanyPolicy companyPolicy) {
//    	companyPolicy.setPolicyId(sequenceGeneratorService.generateSequence(CompanyPolicy.class.getSimpleName()));
//        return companyPolicyRepository.save(companyPolicy);
//    }
//
//    public List<CompanyPolicy> getAllPolicies() {
//        return companyPolicyRepository.findAll();
//    }
//
//    public String getCompanyPolicy() {
//        // Fetch the company policy (e.g., from the database or file)
//        return companyPolicyRepository.findCurrentPolicy();
//    }
//    
//    public boolean deletePolicy(String policyId) {
//        // Check if the policy exists
//        if (companyPolicyRepository.existsById(policyId)) {
//            // Delete the policy
//            companyPolicyRepository.deleteById(policyId);
//            return true;
//        }
//        return false;
//    }
//}
//



package com.example.th.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException; // Assuming you have this exception for handling not found cases
import com.example.th.model.CompanyPolicy;
import com.example.th.repository.CompanyPolicyRepository;

@Service
public class CompanyPolicyService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private CompanyPolicyRepository companyPolicyRepository;

    public CompanyPolicy createPolicy(CompanyPolicy companyPolicy) {
        companyPolicy.setPolicyId(sequenceGeneratorService.generateSequence(CompanyPolicy.class.getSimpleName()));
        return companyPolicyRepository.save(companyPolicy);
    }

    public List<CompanyPolicy> getAllPolicies() {
        return companyPolicyRepository.findAll();
    }

    public CompanyPolicy getPolicyById(String policyId) {
        return companyPolicyRepository.findById(policyId)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + policyId));
    }
    
//    public CompanyPolicy updatePolicy(String policyId, CompanyPolicy updatedPolicy) {
//        CompanyPolicy existingPolicy = companyPolicyRepository.findById(policyId)
//                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with ID: " + policyId));
//
//        // Update necessary fields
//        existingPolicy.setPolicyName(updatedPolicy.getPolicyName());
//        existingPolicy.setPolicyDetails(updatedPolicy.getPolicyDetails());
//        // Add other fields as necessary
//
//        return companyPolicyRepository.save(existingPolicy);
//    }

    public boolean deletePolicy(String policyId) {
        if (companyPolicyRepository.existsById(policyId)) {
            companyPolicyRepository.deleteById(policyId);
            return true;
        }
        return false;
    }
    
    
    public String getCompanyPolicy() {
      // Fetch the company policy (e.g., from the database or file)
      return companyPolicyRepository.findCurrentPolicy();
  }
}

