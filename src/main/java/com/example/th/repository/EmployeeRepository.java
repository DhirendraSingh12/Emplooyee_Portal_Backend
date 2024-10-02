//package com.example.th.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//
//import com.example.th.model.Employee;
//
//public interface EmployeeRepository extends MongoRepository<Employee, String> {
//	
//	 // Find employee by employee ID
//	Optional<Employee> findByEmployeeId(String employeeId);
//
//    // Find all employees by department
//    List<Employee> findByDepartment(String department);
//
////    // Find employees by role
////    List<Employee> findByRole(String role);
//    
//	 @Query(value = "{'employeeId': {$regex: ?0}}", sort = "{'employeeId': -1}")
//    Employee findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(String prefix);
//
//    default int findMaxEmployeeIdNumber(String prefix) {
//        Employee topEmployee = findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(prefix);
//        if (topEmployee == null) {
//            return 0;
//        }
//        String numericPart = topEmployee.getEmployeeId().substring(prefix.length());
//        return Integer.parseInt(numericPart);
//    }
//    
//	 boolean existsByEmail(String email);
//
//	 @Query("SELECT MAX(e.idNumber) FROM Employee e")
//	 Integer findMaxIdNumber();
//}


package com.example.th.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.th.model.Employee;


import java.util.List;
import java.util.Optional;

//public interface EmployeeRepository extends MongoRepository<Employee, String> {
//	
//	
//
//    // Find employee by employee ID
//    Optional<Employee> findByEmployeeId(String employeeId);


@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
	
	Optional<Employee> findByEmployeeId(String employeeId);

    // Find all employees by department
    List<Employee> findByDepartment(String department);

    // Find employees by role
    // List<Employee> findByRole(String role);

    // Find the employee with the highest ID that starts with the given prefix
    @Query(value = "{'employeeId': {$regex: ?0}}", sort = "{'employeeId': -1}")
    List<Employee> findAllByEmployeeIdStartingWithOrderByEmployeeIdDesc(String prefix);
    
//    @Query(value = "{'employeeId': {$regex: ?0}}", sort = "{'employeeId': -1}")
//    Employee findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(String prefix);

    // Check if an email already exists
    boolean existsByEmail(String email);

    // Default method to find the maximum employee number with the given prefix
    default int findMaxEmployeeIdNumber(String prefix) {
        List<Employee> employees = findAllByEmployeeIdStartingWithOrderByEmployeeIdDesc(prefix);
        if (employees.isEmpty()) {
            return 0;
        }
        String numericPart = employees.get(0).getEmployeeId().substring(prefix.length());
        try {
            return Integer.parseInt(numericPart);
        } catch (NumberFormatException e) {
            // Handle parsing error if the numeric part is not properly formatted
            return 0;
        }
    }
}

//public interface EmployeeRepository extends MongoRepository<Employee, String> {
//
//    // Find employee by employee ID
//    Optional<Employee> findByEmployeeId(String employeeId);
//
//    // Find all employees by department
//    List<Employee> findByDepartment(String department);
//
//    // Find employees by role
//    //List<Employee> findByRole(String role);
//
//    // Find the employee with the highest ID that starts with the given prefix
//    @Query(value = "{'employeeId': {$regex: ?0}}", sort = "{'employeeId': -1}")
//    Employee findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(String prefix);
//
//    // Check if an email already exists
//    boolean existsByEmail(String email);
//
//    // Find the maximum employee number with the given prefix
//    default int findMaxEmployeeIdNumber(String prefix) {
//        Employee topEmployee = findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(prefix);
//        if (topEmployee == null) {
//            return 0;
//        }
//        String numericPart = topEmployee.getEmployeeId().substring(prefix.length());
//        try {
//            return Integer.parseInt(numericPart);
//        } catch (NumberFormatException e) {
//            // Handle parsing error if the numeric part is not properly formatted
//            return 0;
//        }
//    }
//}
