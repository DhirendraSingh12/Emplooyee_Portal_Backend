package com.example.th.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.th.model.Employee;
import com.example.th.repository.EmployeeRepository;
import com.example.th.utils.EmployeeIdGenerator;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmployeeIdGenerator employeeIdGenerator;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    
    private static final String EMPLOYEE_ID_PREFIX = "HFX";
    private static final int ID_PADDING_SIZE = 4;

//    public Employee createEmployee(Employee employee) {
//        String employeeId = employeeIdGenerator.generate();
//        employee.setEmployeeId(employeeId);
//        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
//        Employee savedEmployee = employeeRepository.save(employee);
//        emailService.sendEmployeeCredentials(savedEmployee);
//        return savedEmployee;
//    }
//    
//    public Employee createEmployee(Employee employee) {
//        // Generate a unique employee ID
//        String employeeId = employeeIdGenerator.generate();
//        employee.setEmployeeId(employeeId);
//
//        // Validate that the password is not null or empty
//        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
//            throw new IllegalArgumentException("Password cannot be null or empty");
//        }
//
//        // Encode the employee's password
//        String encodedPassword = passwordEncoder.encode(employee.getPassword());
//        employee.setPassword(encodedPassword);
//
//        // Save the employee to the repository
//        Employee savedEmployee = employeeRepository.save(employee);
//
//        // Send employee credentials via email
//        emailService.sendEmployeeCredentials(savedEmployee);
//
//        // Return the saved employee object
//        return savedEmployee;
//    }
//
    
    public Employee createEmployee(Employee employee) {
        // Generate a new employee ID
        String newEmployeeId = employeeIdGenerator.generateEmployeeId();
        employee.setEmployeeId(newEmployeeId);

        // Validate the employee's password
        String password = employee.getPassword();
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        
//     // Set the raw password
        employee.setRawPassword(password);

        // Encode the employee's password
        String encodedPassword = passwordEncoder.encode(password);
        employee.setPassword(encodedPassword);

        // Save the employee to the repository
        Employee savedEmployee = employeeRepository.save(employee);

        // Attempt to send employee credentials via email
        try {
            emailService.sendEmployeeCredentials(savedEmployee);
        } catch (MailException e) {
            // Log the exception and continue, as the employee has been created
            logger.error("Failed to send email to employee: {}. Employee ID: {}",
                    savedEmployee.getEmail(), savedEmployee.getEmployeeId(), e);
        }

        // Return the saved employee object
        return savedEmployee;
    }


    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

//    public Employee updateEmployee(String employeeId, Employee updatedEmployee) {
//        Optional<Employee> existingEmployee = employeeRepository.findById(employeeId);
//        
//        if (existingEmployee.isPresent()) {
//            // Get the existing employee entity
//            Employee employee = existingEmployee.get();
//
//            // Update employee details with the new data
////            employee.setFirstName(updatedEmployee.getFirstName());
////            employee.setLastName(updatedEmployee.getLastName());
//            employee.setEmployeeName(updatedEmployee.getEmployeeName());
//            employee.setGender(updatedEmployee.getGender());
//            employee.setMobile(updatedEmployee.getMobile());
//            employee.setPassword(updatedEmployee.getPassword());
//            employee.setDesignation(updatedEmployee.getDesignation());
//            employee.setDepartment(updatedEmployee.getDepartment());
//            employee.setAddress(updatedEmployee.getAddress());
//            employee.setEmail(updatedEmployee.getEmail());
//            employee.setDob(updatedEmployee.getDob());
//            employee.setEducation(updatedEmployee.getEducation());
//           // employee.setPhoto(updatedEmployee.getPhoto());
//
//            // Save the updated employee back to the repository (MongoDB)
//            return employeeRepository.save(employee);
//        }
//        
//        // If employee not found, return null
//        return null;
//    }
    
    public Employee updateEmployee(String employeeId, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findByEmployeeId(employeeId);
        
        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            // Update fields from updatedEmployee as needed
            existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
            existingEmployee.setGender(updatedEmployee.getGender());
            existingEmployee.setMobile(updatedEmployee.getMobile());
           // existingEmployee.setPassword(updatedEmployee.getPassword());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setEmail(updatedEmployee.getEmail());
            existingEmployee.setDob(updatedEmployee.getDob());
            existingEmployee.setEducation(updatedEmployee.getEducation());
            
            // Save the updated employee back to the repository
            return employeeRepository.save(existingEmployee);
        }

        // If employee not found, return null
        return null;
    }



    public boolean deleteEmployee(String employeeId) {
        Optional<Employee> existingEmployee = employeeRepository.findByEmployeeId(employeeId);
        
        if (existingEmployee.isPresent()) {
            employeeRepository.delete(existingEmployee.get());
            return true; // Employee successfully deleted
        }
        
        return false; // Employee not found
    }

    public Employee findByEmployeeId(String employeeId) {
        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
    }


    public List<Employee> addEmployeesInBulk(List<Employee> employees) {
        // Get the current maximum employee ID number
        int currentMaxIdNumber = employeeRepository.findMaxEmployeeIdNumber(EMPLOYEE_ID_PREFIX);

        // Generate unique employee IDs and encrypt passwords
        List<Employee> processedEmployees = IntStream.range(0, employees.size())
                .mapToObj(i -> {
                    Employee employee = employees.get(i);

                    // Generate employee ID
                    String employeeId = generateEmployeeId(currentMaxIdNumber + i + 1);
                    employee.setEmployeeId(employeeId);

                    // Encrypt the password
                    String encodedPassword = passwordEncoder.encode(employee.getPassword());
                    employee.setPassword(encodedPassword);

                    // Send email with employee ID and raw password
                    emailService.sendEmployeeCredentials(employee.getEmail(), employeeId, employee.getPassword());

                    return employee;
                })
                .collect(Collectors.toList());

        // Save employees in the database
        return employeeRepository.saveAll(processedEmployees);
    }

    private String generateEmployeeId(int idNumber) {
        // Pads the number with leading zeros, e.g., 1 -> 0001
        String paddedNumber = String.format("%0" + ID_PADDING_SIZE + "d", idNumber);
        return EMPLOYEE_ID_PREFIX + paddedNumber;
    }

    public Employee getEmployeeById(String employeeId) {
        // Retrieve employee by ID from the repository
        Optional<Employee> employeeOptional = employeeRepository.findByEmployeeId(employeeId);

        // Return the employee if found, otherwise throw an exception or return null
        if (employeeOptional.isPresent()) {
            return employeeOptional.get();
        } else {
            // Handle the case where employee is not found
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
            // Alternatively, you could return null and handle this case in the controller
        }
    }
}
