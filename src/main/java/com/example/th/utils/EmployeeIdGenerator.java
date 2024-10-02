//package com.example.th.utils;
//
//import org.springframework.stereotype.Component;
//
//import java.util.Random;
//
//@Component
//public class EmployeeIdGenerator {
//
//    private static final String EMPLOYEE_ID_PREFIX = "HFX";
//    private static final int ID_PADDING_SIZE = 4; // To ensure 4-digit ID
//
//    // Generates an employee ID with the given idNumber (sequential)
//    public String generate(int idNumber) {
//        // Pads the number with leading zeros, e.g., 1 -> 0001
//        String paddedNumber = String.format("%0" + ID_PADDING_SIZE + "d", idNumber);
//        return EMPLOYEE_ID_PREFIX + paddedNumber;
//    }
//}

package com.example.th.utils;


import java.util.List;

import org.springframework.stereotype.Component;

import com.example.th.model.Employee;
import com.example.th.repository.EmployeeRepository;



@Component
public class EmployeeIdGenerator {

    private static final String PREFIX = "HFX";
    private static final int ID_LENGTH = 4; // Length of the numeric part

    private final EmployeeRepository employeeRepository;

    public EmployeeIdGenerator(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public String generateEmployeeId() {
        // Find the highest existing ID with the prefix
        List<Employee> employees = employeeRepository.findAllByEmployeeIdStartingWithOrderByEmployeeIdDesc(PREFIX);
        String newId;

        if (employees.isEmpty()) {
            // If no employee exists with this prefix, start with the first number
            newId = PREFIX + String.format("%0" + ID_LENGTH + "d", 1);
        } else {
            // Extract the numeric part and increment it
            String currentId = employees.get(0).getEmployeeId();
            String numericPart = currentId.substring(PREFIX.length());
            int nextNumber = Integer.parseInt(numericPart) + 1;
            newId = PREFIX + String.format("%0" + ID_LENGTH + "d", nextNumber);
        }

        return newId;
    }
}


//@Component
//public class EmployeeIdGenerator {
//	
//	@Autowired
//    private EmployeeRepository employeeRepository;
//
//    private static final String EMPLOYEE_ID_PREFIX = "HFX";
//    private static final int ID_PADDING_SIZE = 4;
//
//    public String generateEmployeeId() {
//        // Get the next ID number
//        int nextIdNumber = findMaxEmployeeIdNumber(EMPLOYEE_ID_PREFIX) + 1;
//
//        // Format the ID with the prefix and padded number
//        return EMPLOYEE_ID_PREFIX + String.format("%0" + ID_PADDING_SIZE + "d", nextIdNumber);
//    }
//
//    private int findMaxEmployeeIdNumber(String prefix) {
//        Employee topEmployee = employeeRepository.findTopByEmployeeIdStartsWithOrderByEmployeeIdDesc(prefix);
//        if (topEmployee == null) {
//            return 0;
//        }
//        String numericPart = topEmployee.getEmployeeId().substring(prefix.length());
//        try {
//            return Integer.parseInt(numericPart);
//        } catch (NumberFormatException e) {
//            return 0;
//        }
//    }
//}

//    private static final String EMPLOYEE_ID_PREFIX = "HFX";
//    private static final int ID_PADDING_SIZE = 4; // To ensure 4-digit ID
//    
//    
// // Generates an employee ID with the given idNumber (sequential)
//  public String generate(int idNumber) {
//      // Pads the number with leading zeros, e.g., 1 -> 0001
//      String paddedNumber = String.format("%0" + ID_PADDING_SIZE + "d", idNumber);
//      return EMPLOYEE_ID_PREFIX + paddedNumber;
//  }
//
//    // Generates the next employee ID based on the current employee ID
//    public String generateNextId(String currentEmployeeId) {
//        // Extract numeric part from the current employee ID
//        String numericPart = currentEmployeeId.substring(EMPLOYEE_ID_PREFIX.length());
//        
//        // Convert the numeric part to an integer
//        int idNumber = Integer.parseInt(numericPart);
//        
//        // Increment the numeric part
//        idNumber++;
//        
//        // Format the new ID with leading zeros (e.g., 0001)
//        String paddedNumber = String.format("%0" + ID_PADDING_SIZE + "d", idNumber);
//        
//        // Return the new employee ID
//        return EMPLOYEE_ID_PREFIX + paddedNumber;
//    }
//}
