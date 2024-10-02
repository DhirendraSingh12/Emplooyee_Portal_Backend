package com.example.th.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "documents")
public class My_Document {
	
	
	
//	private String id;
	
	@Id
	private String documentId;
	private String employeeId;
	private String employeeName;
	private String documentType; // e.g., "PAN", "AADHAR"
	private String documentUrl; // URL to the uploaded document
	private boolean uploadStatus; // e.g., "UPLOADED", "NOT_UPLOADED"
	private LocalDateTime uploadedDate;
	 private String verifyStatus; // e.g., "VERIFIED", "NOT_VERIFIED", "PENDING"
	 private boolean approved;
	 private String view;
	
	// Getter and Setter
	
	
	 
	 
	public String getEmployeeId() {
		return employeeId;
	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getDocumentUrl() {
		return documentUrl;
	}
	public void setDocumentUrl(String documentUrl) {
		this.documentUrl = documentUrl;
	}
	
	public boolean isUploadStatus() {
		return uploadStatus;
	}
	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public LocalDateTime getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(LocalDateTime localDateTime) {
		this.uploadedDate = localDateTime;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	

	 


	


}
