package com.example.th.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.th.model.DataSummary;
import com.example.th.model.Expense;
import com.example.th.model.TimeOffRequest;
import com.example.th.model.Timesheet;
import com.example.th.service.ExpenseService;
import com.example.th.service.TimeOffRequestService;
import com.example.th.service.TimesheetService;

@RestController
@RequestMapping("/api")
public class DataController {

    @Autowired
    private TimeOffRequestService timeOffRequestService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private TimesheetService timesheetService;

    @GetMapping("/data/{empId}")
    public DataSummary getDataForMonth(
        @PathVariable String empId,
        @RequestParam LocalDate month) {
        
        List<TimeOffRequest> timeOffRequests = timeOffRequestService.getByEmployeeIdAndMonth(empId, month);
        List<Expense> expenses = expenseService.getByEmployeeIdAndMonth(empId, month);
        List<Timesheet> timesheets = timesheetService.getByEmployeeIdAndMonth(empId, month);
        
        return new DataSummary(timeOffRequests, expenses, timesheets);
    }
}