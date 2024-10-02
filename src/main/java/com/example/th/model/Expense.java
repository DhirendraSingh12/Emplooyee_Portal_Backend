package com.example.th.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "expenses")
public class Expense {

	
	//private String id;
	
	@Id
	private String expenseId;
    private String employeeId;
    private String employeeName;
    private LocalDate expenseDate;
    private String expenseType;
    private String expenseDescription;
    private double amount;
    private String status;
    
    // Field for the receipt (file name or file path)
    private String receiptFileName;

    // If you want to store the file data as binary, you can use the following:
    // private byte[] receiptFileData;

    // Getters and Setters
    
    
    

    
    public String getEmployeeId() {
        return employeeId;
    }

//    public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptFileName() {
        return receiptFileName;
    }

    public void setReceiptFileName(String receiptFileName) {
        this.receiptFileName = receiptFileName;
    }

    // Uncomment if you are storing the file as binary data:
    // public byte[] getReceiptFileData() {
    //     return receiptFileData;
    // }

    // public void setReceiptFileData(byte[] receiptFileData) {
    //     this.receiptFileData = receiptFileData;
    // }
}
