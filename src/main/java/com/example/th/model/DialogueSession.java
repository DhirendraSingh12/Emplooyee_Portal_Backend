package com.example.th.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Document(collection = "dialogue_sessions")
public class DialogueSession {

    
	
//	private String id;
	
	@Id
	private String sessionId;
    private String employeeId;
    private String meetingType;  // Use String for meeting type
    private LocalDate reviewDate; // Use LocalDate for review date
    private String commentsAndNotes;
    private LocalDate nextMeetingDate; // Use LocalDate for next meeting date
    private String meetingURL;

    private boolean isEditable;

	public DialogueSession() {
		super();
	}

	public DialogueSession(String sessionId,String meetingType, String employeeId, LocalDate reviewDate,
			String commentsAndNotes, LocalDate nextMeetingDate, String meetingURL,  boolean isEditable) {
		super();
	//	this.id=id;
		this.sessionId=sessionId;
		this.meetingType = meetingType;
		this.employeeId = employeeId;
		this.reviewDate = reviewDate;
		this.commentsAndNotes = commentsAndNotes;
		this.nextMeetingDate = nextMeetingDate;
		this.meetingURL = meetingURL;
		this.isEditable = isEditable;
	}

	

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getMeetingType() {
		return meetingType;
	}

	public void setMeetingType(String meetingType) {
		this.meetingType = meetingType;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(LocalDate reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getCommentsAndNotes() {
		return commentsAndNotes;
	}

	public void setCommentsAndNotes(String commentsAndNotes) {
		this.commentsAndNotes = commentsAndNotes;
	}

	public LocalDate getNextMeetingDate() {
		return nextMeetingDate;
	}

	public void setNextMeetingDate(LocalDate nextMeetingDate) {
		this.nextMeetingDate = nextMeetingDate;
	}
	
	

	public String getMeetingURL() {
		return meetingURL;
	}

	public void setMeetingURL(String meetingURL) {
		this.meetingURL = meetingURL;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	

	
	
    
}

    