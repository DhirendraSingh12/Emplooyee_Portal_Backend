package com.example.th.model;

import java.util.List;

public class DataSummary {
    private List<TimeOffRequest> timeOffRequests;
    private List<Expense> expenses;
    private List<Timesheet> timesheets;

    public DataSummary(List<TimeOffRequest> timeOffRequests, List<Expense> expenses, List<Timesheet> timesheets) {
        this.timeOffRequests = timeOffRequests;
        this.expenses = expenses;
        this.timesheets = timesheets;
    }

    
 // Getters and Setters
    
	public List<TimeOffRequest> getTimeOffRequests() {
		return timeOffRequests;
	}

	public void setTimeOffRequests(List<TimeOffRequest> timeOffRequests) {
		this.timeOffRequests = timeOffRequests;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public List<Timesheet> getTimesheets() {
		return timesheets;
	}

	public void setTimesheets(List<Timesheet> timesheets) {
		this.timesheets = timesheets;
	}
    
    
}