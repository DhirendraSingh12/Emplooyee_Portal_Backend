//package com.example.th.service;
//
//import java.time.LocalDate;
//import java.time.temporal.TemporalAdjusters;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.exception.ResourceNotFoundException;
//import com.example.th.model.Employee;
//import com.example.th.model.Timesheet;
//import com.example.th.repository.TimesheetRepository;
//import com.example.th.repository.EmployeeRepository;
//
//@Service
//public class TimesheetService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private TimesheetRepository timesheetRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private EmailService emailService;
//
//    // Create a new timesheet for an employee
//    public Timesheet createTimesheetForEmployee(String employeeId, Timesheet timesheet) {
//        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
//        if (employee.isPresent()) {
//            timesheet.setEmployeeId(employeeId);
//            // timesheet.setEmployeeName(employee.get().getName()); // Uncomment if needed
//
//            // Determine attendance status
//            String attendanceStatus = determineAttendanceStatus(timesheet.getInDate());
//            timesheet.setAttendanceStatus(attendanceStatus);
//
//            timesheet.setStatus("Pending");
//            
//            timesheet.setTimesheetId(sequenceGeneratorService.generateSequence(Timesheet.class.getSimpleName()));
//            return timesheetRepository.save(timesheet);
//        } else {
//            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
//        }
//    }
//
//    // Approve a timesheet
//    public boolean approveTimesheet(String timesheetId) {
//        // Find the timesheet by ID
//        Optional<Timesheet> timesheetOptional = timesheetRepository.findById(timesheetId);
//        
//        if (timesheetOptional.isPresent()) {
//            Timesheet timesheet = timesheetOptional.get();
//            
//            // Approve the timesheet
//            timesheet.setStatus("Approved");
//
//            // Only regularize if the attendance status is not a weekend or holiday
//            if (!"Weekend".equals(timesheet.getAttendanceStatus()) && !"Holiday".equals(timesheet.getAttendanceStatus())) {
//                timesheet.setAttendanceStatus("Regularized");
//                
//                // Calculate total working hours and set them
//                timesheet.setHours(calculateTotalHours(timesheet));
//            }
//
//            // Save the updated timesheet
//            timesheetRepository.save(timesheet);
//            return true;
//        }
//
//        return false;
//    }
//    
//    // Send reminder for pending timesheets
//    public void sendPendingTimesheetReminder(String employeeId) {
//        Employee employee = employeeRepository.findByEmployeeId(employeeId)
//            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
//        
//        List<Timesheet> pendingTimesheets = timesheetRepository.findByEmployeeIdAndStatus(employeeId, "Pending");
//        if (!pendingTimesheets.isEmpty()) {
//            emailService.sendEmail(employee.getEmail(), "Pending Timesheets Reminder",
//                "Please complete your pending timesheets by Friday 5:00 PM.");
//        }
//    }
//
//    // Get timesheets for a specific employee
//    public List<Timesheet> getTimesheetsForEmployee(String employeeId) {
//        return timesheetRepository.findByEmployeeId(employeeId);
//    }
//
//    // Get timesheets for an employee within a specific month
//    public List<Timesheet> getByEmployeeIdAndMonth(String empId, LocalDate month) {
//        LocalDate startOfMonth = month.withDayOfMonth(1);
//        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
//        return timesheetRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
//    }
//
//    // Submit a timesheet
//    public Timesheet submitTimesheet(Timesheet timesheet) {
//        // Calculate working hours before saving
//        timesheet.calculateHours();
//        
//        // Set default status and attendanceStatus
//        timesheet.setStatus("Pending");
//        timesheet.setAttendanceStatus("Regularized");
//        
//        // Save the timesheet in MongoDB
//        return timesheetRepository.save(timesheet);
//    }
//
//    // Get all timesheets
//    public List<Timesheet> getAllTimesheets() {
//        return timesheetRepository.findAll();
//    }
//
//    // Calculate total working hours for a timesheet
//    private String calculateTotalHours(Timesheet timesheet) {
//        int inMinutes = convertToMinutes(timesheet.getInTimeHH(), timesheet.getInTimeMM(), timesheet.getInPeriod());
//        int outMinutes = convertToMinutes(timesheet.getOutTimeHH(), timesheet.getOutTimeMM(), timesheet.getOutPeriod());
//
//        int totalMinutes = outMinutes - inMinutes;
//        if (totalMinutes < 0) {
//            totalMinutes += 24 * 60; // Adjust for cases where out time is past midnight
//        }
//
//        int hours = totalMinutes / 60;
//        int minutes = totalMinutes % 60;
//
//        return String.format("%02d:%02d", hours, minutes);
//    }
//
//    // Convert time to minutes
//    private int convertToMinutes(int hours, int minutes, String period) {
//        if ("PM".equals(period) && hours < 12) {
//            hours += 12;
//        }
//        if ("AM".equals(period) && hours == 12) {
//            hours = 0;
//        }
//        return hours * 60 + minutes;
//    }
//
//    // Determine attendance status based on the date
//    private String determineAttendanceStatus(LocalDate inDate) {
//        if (isWeekend(inDate)) {
//            return "Weekend";
//        } else if (isHoliday(inDate)) {
//            return "Holiday";
//        } else {
//            return "Regularized";
//        }
//    }
//
//    // Check if a date is a weekend
//    private boolean isWeekend(LocalDate date) {
//        return date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
//    }
//
//    // Check if a date is a holiday
//    private boolean isHoliday(LocalDate date) {
//        // Implement your own holiday check logic here
//        return false; // Placeholder implementation
//    }
//    
//    public boolean approveTimesheetsByEmployeeId(String employeeId) {
//        List<Timesheet> timesheets = timesheetRepository.findByEmployeeId(employeeId);
//        
//        if (!timesheets.isEmpty()) {
//            timesheets.forEach(timesheet -> {
//                timesheet.setStatus("Approved");
//                timesheetRepository.save(timesheet);
//            });
//            return true; // Timesheets approved
//        }
//        
//        return false; // No timesheets found for the employee
//    }
//    
//    public boolean rejectTimesheet(String timesheetId) {
//        Optional<Timesheet> optionalTimesheet = timesheetRepository.findById(timesheetId);
//        
//        if (optionalTimesheet.isPresent()) {
//            Timesheet timesheet = optionalTimesheet.get();
//            timesheet.setStatus("Rejected");
//            timesheetRepository.save(timesheet);
//            return true; // Timesheet rejected
//        }
//        
//        return false; // Timesheet not found
//    }
//}



package com.example.th.service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Employee;
import com.example.th.model.Timesheet;
import com.example.th.repository.TimesheetRepository;
import com.example.th.repository.EmployeeRepository;

@Service
public class TimesheetService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private TimesheetRepository timesheetRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmailService emailService;

    // Create a new timesheet for an employee
    @SuppressWarnings("unused")
	public Timesheet createTimesheetForEmployee(String employeeId, Timesheet timesheet) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        timesheet.setEmployeeId(employeeId);
        String attendanceStatus = determineAttendanceStatus(timesheet.getInDate());
        timesheet.setAttendanceStatus(attendanceStatus);
        timesheet.setStatus("Pending");
        timesheet.setTimesheetId(sequenceGeneratorService.generateSequence(Timesheet.class.getSimpleName()));
        return timesheetRepository.save(timesheet);
    }

    // Get a timesheet by its ID
    public Timesheet getTimesheetById(String timesheetId) {
        return timesheetRepository.findById(timesheetId)
            .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found with ID: " + timesheetId));
    }

    // Update a timesheet
    public Timesheet updateTimesheet(String timesheetId, Timesheet updatedTimesheet) {
        Timesheet existingTimesheet = timesheetRepository.findById(timesheetId)
            .orElseThrow(() -> new ResourceNotFoundException("Timesheet not found with ID: " + timesheetId));

        // Update fields as necessary
        existingTimesheet.setInDate(updatedTimesheet.getInDate());
        existingTimesheet.setInTimeHH(updatedTimesheet.getInTimeHH());
        existingTimesheet.setInTimeMM(updatedTimesheet.getInTimeMM());
        existingTimesheet.setInPeriod(updatedTimesheet.getInPeriod());
        existingTimesheet.setOutDate(updatedTimesheet.getOutDate());
        existingTimesheet.setOutTimeHH(updatedTimesheet.getOutTimeHH());
        existingTimesheet.setOutTimeMM(updatedTimesheet.getOutTimeMM());
        existingTimesheet.setOutPeriod(updatedTimesheet.getOutPeriod());
        
        // Calculate attendance status and total hours
        existingTimesheet.setAttendanceStatus(determineAttendanceStatus(updatedTimesheet.getInDate()));
        existingTimesheet.setHours(calculateTotalHours(existingTimesheet));

        return timesheetRepository.save(existingTimesheet);
    }

    // Approve a timesheet
    public boolean approveTimesheet(String timesheetId) {
        Timesheet timesheet = getTimesheetById(timesheetId);
        timesheet.setStatus("Approved");
        
        if (!"Weekend".equals(timesheet.getAttendanceStatus()) && !"Holiday".equals(timesheet.getAttendanceStatus())) {
            timesheet.setAttendanceStatus("Regularized");
            timesheet.setHours(calculateTotalHours(timesheet));
        }

        timesheetRepository.save(timesheet);
        return true;
    }

    // Reject a timesheet
    public boolean rejectTimesheet(String timesheetId) {
        Timesheet timesheet = getTimesheetById(timesheetId);
        timesheet.setStatus("Rejected");
        timesheetRepository.save(timesheet);
        return true;
    }

    // Send reminder for pending timesheets
    public void sendPendingTimesheetReminder(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        List<Timesheet> pendingTimesheets = timesheetRepository.findByEmployeeIdAndStatus(employeeId, "Pending");
        if (!pendingTimesheets.isEmpty()) {
            emailService.sendEmail(employee.getEmail(), "Pending Timesheets Reminder",
                "Please complete your pending timesheets by Friday 5:00 PM.");
        }
    }

    // Get timesheets for a specific employee
    public List<Timesheet> getTimesheetsForEmployee(String employeeId) {
        return timesheetRepository.findByEmployeeId(employeeId);
    }

    // Get all timesheets
    public List<Timesheet> getAllTimesheets() {
        return timesheetRepository.findAll();
    }

    // Calculate total working hours for a timesheet
    private String calculateTotalHours(Timesheet timesheet) {
        int inMinutes = convertToMinutes(timesheet.getInTimeHH(), timesheet.getInTimeMM(), timesheet.getInPeriod());
        int outMinutes = convertToMinutes(timesheet.getOutTimeHH(), timesheet.getOutTimeMM(), timesheet.getOutPeriod());
        
        int totalMinutes = outMinutes - inMinutes;
        if (totalMinutes < 0) {
            totalMinutes += 24 * 60; // Adjust for cases where out time is past midnight
        }

        int hours = totalMinutes / 60;
        int minutes = totalMinutes % 60;
        
        return String.format("%02d:%02d", hours, minutes);
    }

    // Convert time to minutes
    private int convertToMinutes(int hours, int minutes, String period) {
        if ("PM".equals(period) && hours < 12) {
            hours += 12;
        }
        if ("AM".equals(period) && hours == 12) {
            hours = 0;
        }
        return hours * 60 + minutes;
    }

    // Determine attendance status based on the date
    private String determineAttendanceStatus(LocalDate inDate) {
        if (isWeekend(inDate)) {
            return "Weekend";
        } else if (isHoliday(inDate)) {
            return "Holiday";
        } else {
            return "Regularized";
        }
    }

    // Check if a date is a weekend
    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == java.time.DayOfWeek.SATURDAY || date.getDayOfWeek() == java.time.DayOfWeek.SUNDAY;
    }

    // Check if a date is a holiday
    private boolean isHoliday(LocalDate date) {
        // Implement your own holiday check logic here
        return false; // Placeholder implementation
    }

    public boolean approveTimesheetsByEmployeeId(String employeeId) {
        List<Timesheet> timesheets = timesheetRepository.findByEmployeeId(employeeId);
        
        if (!timesheets.isEmpty()) {
            timesheets.forEach(timesheet -> {
                timesheet.setStatus("Approved");
                timesheetRepository.save(timesheet);
            });
            return true; // Timesheets approved
        }
        
        return false; // No timesheets found for the employee
    }
    
 // Submit a timesheet
  public Timesheet submitTimesheet(Timesheet timesheet) {
      // Calculate working hours before saving
      timesheet.calculateHours();
      
      // Set default status and attendanceStatus
      timesheet.setStatus("Pending");
      timesheet.setAttendanceStatus("Regularized");
      
      timesheet.setTimesheetId(sequenceGeneratorService.generateSequence(Timesheet.class.getSimpleName()));
      
      // Save the timesheet in MongoDB
      return timesheetRepository.save(timesheet);
  }
  
// Get timesheets for an employee within a specific month
  public List<Timesheet> getByEmployeeIdAndMonth(String empId, LocalDate month) {
	  LocalDate startOfMonth = month.withDayOfMonth(1);
	  LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
	  return timesheetRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
  }

    
}
