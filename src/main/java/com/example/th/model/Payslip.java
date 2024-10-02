package com.example.th.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Document(collection = "payslips")
public class Payslip {

    
	@Id
	private String id;
	
	@Indexed(unique = true)
    private String employeeId;
    private LocalDate month;
    private String status; // e.g., Pending, Approved
    private String salarySlipUrl; // URL or file path for the generated payslip PDF
    private String filepath;

    // Getters and Setters

    

    public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSalarySlipUrl() {
        return salarySlipUrl;
    }

    public void setSalarySlipUrl(String salarySlipUrl) {
        this.salarySlipUrl = salarySlipUrl;
    }

	public String getFilePath() {
		return filepath;
	}

	public void setFilePath(String filepath) {
		this.filepath = filepath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	
}
