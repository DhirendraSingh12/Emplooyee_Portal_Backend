package com.example.th.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leave_requests")
public class TimeOffRequest {
	
	
	
	//private String id;
	
	@Id
	private String leaveId;
	private String EmployeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String partialDays; // "HALF_DAY" or "FULL_DAY"
    private String type; // "CASUAL", "SICK", "OTHER"
    private String reason; // Reason for the leave
    private String status; // "PENDING", "APPROVED", "REJECTED"
    
    
    // Getter and Setter 
    
    public String getEmployeeId() {
		return EmployeeId;
	}
	public void setEmployeeId(String employeeId) {
		EmployeeId = employeeId;
	}
    
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getPartialDays() {
		return partialDays;
	}
	public void setPartialDays(String partialDays) {
		this.partialDays = partialDays;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	
    
	
}
