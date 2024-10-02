package com.example.th.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.th.model.Admin;
import com.example.th.model.Employee;
import com.example.th.model.SuperAdmin;
import com.example.th.repository.AdminRepository;
import com.example.th.repository.EmployeeRepository;
import com.example.th.repository.SuperAdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SuperAdminRepository superAdminRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(SuperAdminRepository superAdminRepository, EmployeeRepository employeeRepository, AdminRepository adminRepository) {
        this.superAdminRepository = superAdminRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the user is a SuperAdmin
        Optional<SuperAdmin> superAdminOpt = superAdminRepository.findByUsername(username);
        if (superAdminOpt.isPresent()) {
            SuperAdmin superAdmin = superAdminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    superAdmin.getUsername(),
                    superAdmin.getPassword(),
                    superAdmin.getAuthorities() // Make sure to implement this in your SuperAdmin class
            );
        }
        
     // Then, check if the user is an Admin
        Optional<Admin> adminOptional = adminRepository.findByUsername(username);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    admin.getAuthorities()  // Add roles or authorities here if necessary
            );
        }

        // Check if the user is an Employee
        Optional<Employee> employeeOpt = employeeRepository.findByEmployeeId(username); // Assuming employeeId is username
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    employee.getEmployeeId(),
                    employee.getPassword(),
                    
                 //   employee.getAuthorities(),
                    new ArrayList<>()// Make sure to implement this in your Employee class
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
        

    
