//package com.example.th.repository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.mongodb.repository.Query;
//
//import com.example.th.model.Timesheet;
//
//public interface TimesheetRepository extends MongoRepository<Timesheet, String> {
//	List<Timesheet> findPendingTimesheetsByEmployeeId(String employeeId, LocalDate cutOffDate);
//	
//	 // Find timesheets by employee ID
//    List<Timesheet> findByEmployeeId(String employeeId);
//
//    // Find timesheets by date
//    List<Timesheet> findByDate(LocalDate date);
//
//    // Find timesheets by status
//    List<Timesheet> findByStatus(String status);
//
//	List<Timesheet> findByEmployeeIdAndStatus(String employeeId, String string);
//	
//	@Query("{ 'employeeId': ?0, 'inDate': { $gte: ?1, $lte: ?2 } }")
//    List<Timesheet> findByEmployeeIdAndMonth(String employeeId, LocalDate startOfMonth, LocalDate endOfMonth);
//}
//
//
package com.example.th.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.th.model.Timesheet;

public interface TimesheetRepository extends MongoRepository<Timesheet, String> {
	
	// Find pending timesheets by employee ID and with a cutoff date
	@Query("{ 'employeeId': ?0, 'status': 'Pending', 'inDate': { $lte: ?1 } }")
	List<Timesheet> findPendingTimesheetsByEmployeeIdAndCutOffDate(String employeeId, LocalDate cutOffDate);

	// Find timesheets by employee ID
	List<Timesheet> findByEmployeeId(String employeeId);

	// Find timesheets by check-in date
	List<Timesheet> findByInDate(LocalDate inDate);

	// Find timesheets by check-out date
	List<Timesheet> findByOutDate(LocalDate outDate);

	// Find timesheets by status
	List<Timesheet> findByStatus(String status);

	// Find timesheets by employee ID and status
	List<Timesheet> findByEmployeeIdAndStatus(String employeeId, String status);

	// Find timesheets by employee ID and date range (for a specific month)
	@Query("{ 'employeeId': ?0, 'inDate': { $gte: ?1, $lte: ?2 } }")
	List<Timesheet> findByEmployeeIdAndMonth(String employeeId, LocalDate startOfMonth, LocalDate endOfMonth);
}

