package com.example.th.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "timesheets")
public class Timesheet {

    
	
	//private String id;
	
	@Id
	private String timesheetId;
	
	private String employeeId;
	private String employeeName;
    private LocalDate inDate; // Date of check-in
    private int inTimeHH; // Check-in hour (1-12)
    private int inTimeMM; // Check-in minute (0-59)
    private String inPeriod; // "AM" or "PM"
    private LocalDate outDate; // Date of check-out
    private int outTimeHH; // Check-out hour (1-12)
    private int outTimeMM; // Check-out minute (0-59)
    private String outPeriod; // "AM" or "PM"
    
    // Fields to be updated upon submission
    private String hours; // Total working hours for that day (in HH:MM format)
    private String attendanceStatus; // "Regularized", "Weekend", "Holiday"
    private String status; // "Pending", "Approved", "Rejected"
    
    // Default constructor
    public Timesheet() {}

    // Constructor to initialize timesheet object
    public Timesheet( String timesheetId, String employeeId, String employeeName, LocalDate inDate, int inTimeHH, int inTimeMM, String inPeriod,
                     LocalDate outDate, int outTimeHH, int outTimeMM, String outPeriod) {
      //  this.id =id;
        this.timesheetId=timesheetId;
    	this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.inDate = inDate;
        this.inTimeHH = inTimeHH;
        this.inTimeMM = inTimeMM;
        this.inPeriod = inPeriod;
        this.outDate = outDate;
        this.outTimeHH = outTimeHH;
        this.outTimeMM = outTimeMM;
        this.outPeriod = outPeriod;
    }

    // Getters and Setters
    
    
    

    public String getEmployeeId() {
        return employeeId;
    }

    

	public String getTimesheetId() {
		return timesheetId;
	}

	public void setTimesheetId(String timesheetId) {
		this.timesheetId = timesheetId;
	}

//	public String getId() {
//		return id;
//	}
//
//	
//	public void setId(String id) {
//		this.id = id;
//	}

	public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getInDate() {
        return inDate;
    }

    public void setInDate(LocalDate inDate) {
        this.inDate = inDate;
    }

    public int getInTimeHH() {
        return inTimeHH;
    }

    public void setInTimeHH(int inTimeHH) {
        if (inTimeHH < 1 || inTimeHH > 12) {
            throw new IllegalArgumentException("Hour must be between 1 and 12");
        }
        this.inTimeHH = inTimeHH;
    }

    public int getInTimeMM() {
        return inTimeMM;
    }

    public void setInTimeMM(int inTimeMM) {
        if (inTimeMM < 0 || inTimeMM > 59) {
            throw new IllegalArgumentException("Minute must be between 0 and 59");
        }
        this.inTimeMM = inTimeMM;
    }

    public String getInPeriod() {
        return inPeriod;
    }

    public void setInPeriod(String inPeriod) {
        if (!inPeriod.equals("AM") && !inPeriod.equals("PM")) {
            throw new IllegalArgumentException("Period must be 'AM' or 'PM'");
        }
        this.inPeriod = inPeriod;
    }

    public LocalDate getOutDate() {
        return outDate;
    }

    public void setOutDate(LocalDate outDate) {
        this.outDate = outDate;
    }

    public int getOutTimeHH() {
        return outTimeHH;
    }

    public void setOutTimeHH(int outTimeHH) {
        if (outTimeHH < 1 || outTimeHH > 12) {
            throw new IllegalArgumentException("Hour must be between 1 and 12");
        }
        this.outTimeHH = outTimeHH;
    }

    public int getOutTimeMM() {
        return outTimeMM;
    }

    public void setOutTimeMM(int outTimeMM) {
        if (outTimeMM < 0 || outTimeMM > 59) {
            throw new IllegalArgumentException("Minute must be between 0 and 59");
        }
        this.outTimeMM = outTimeMM;
    }

    public String getOutPeriod() {
        return outPeriod;
    }

    public void setOutPeriod(String outPeriod) {
        if (!outPeriod.equals("AM") && !outPeriod.equals("PM")) {
            throw new IllegalArgumentException("Period must be 'AM' or 'PM'");
        }
        this.outPeriod = outPeriod;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String string) {
        this.hours = string;
    }

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//    // Method to calculate total working hours
//    public void calculateHours() {
//        LocalTime checkInTime = LocalTime.of(convertTo24HourFormat(inTimeHH, inPeriod), inTimeMM);
//        LocalTime checkOutTime = LocalTime.of(convertTo24HourFormat(outTimeHH, outPeriod), outTimeMM);
//
//        long minutesWorked = ChronoUnit.MINUTES.between(checkInTime, checkOutTime);
//
//        long hoursPart = minutesWorked / 60;
//        long minutesPart = minutesWorked % 60;
//
//        this.hours = String.format("%02d:%02d", hoursPart, minutesPart);
//    }

 // Calculate the total working hours and update the 'hours' field as a double
    public void calculateHours() {
        // Calculate total hours worked as a double
        double totalHours = calculateWorkingHours(inTimeHH, inTimeMM, inPeriod, outTimeHH, outTimeMM, outPeriod);
        
        // Convert double to string, formatting to two decimal places
        this.hours = String.format("%.2f", totalHours);
    }

    // Private helper method to calculate total working hours
    private double calculateWorkingHours(int inHH, int inMM, String inPeriod, int outHH, int outMM, String outPeriod) {
        // Convert times to 24-hour format for easier calculation
        if ("PM".equals(inPeriod) && inHH != 12) inHH += 12;
        if ("AM".equals(inPeriod) && inHH == 12) inHH = 0;
        if ("PM".equals(outPeriod) && outHH != 12) outHH += 12;
        if ("AM".equals(outPeriod) && outHH == 12) outHH = 0;

        // Calculate total hours worked as a double (e.g., 7.5 for 7 hours 30 minutes)
        double inTime = inHH + inMM / 60.0;
        double outTime = outHH + outMM / 60.0;
        
        // Return the difference (outTime - inTime)
        return outTime - inTime;
    }
    
//    // Helper method to convert 12-hour time to 24-hour format
//    private int convertTo24HourFormat(int hour, String period) {
//        if (period.equals("PM") && hour != 12) {
//            return hour + 12;
//        } else if (period.equals("AM") && hour == 12) {
//            return 0;
//        } else {
//            return hour;
//        }
//    }
}
