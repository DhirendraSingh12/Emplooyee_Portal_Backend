package com.example.th.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "assets")
public class Asset_Managenment {

    
   
	
	//private String id;
	
	@Id
	private String assetId;
	
    private String employeeId;
	private String employeeName;
    private String assetType;
    private LocalDate dateGiven;
    private double estimatedValue;
    private String serialNumber;
    private String insuranceDetails;
    private String status; 
    
    // Getters and setters
    
	
	public String getEmployeeId() {
		return employeeId;
	}
	
	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

//	public String getId() {
//		return id;
//	}
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
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public LocalDate getDateGiven() {
		return dateGiven;
	}
	public void setDateGiven(LocalDate dateGiven) {
		this.dateGiven = dateGiven;
	}
	public double getEstimatedValue() {
		return estimatedValue;
	}
	public void setEstimatedValue(double estimatedValue) {
		this.estimatedValue = estimatedValue;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getInsuranceDetails() {
		return insuranceDetails;
	}
	public void setInsuranceDetails(String insuranceDetails) {
		this.insuranceDetails = insuranceDetails;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    
}