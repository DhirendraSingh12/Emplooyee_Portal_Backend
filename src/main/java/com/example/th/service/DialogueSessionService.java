//package com.example.th.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import com.example.th.model.DialogueSession;
//import com.example.th.model.Employee;
//import com.example.th.repository.DialogueSessionRepository;
//import com.example.th.repository.EmployeeRepository;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class DialogueSessionService {
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//	
//    @Autowired
//    private DialogueSessionRepository repository;
//    
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    
//    @Autowired
//    private JavaMailSender emailSender;  // Inject JavaMailSender
//
//    private String getEmployeeEmail(String employeeId) {
//        // Fetch the Employee object from the database using the employeeId
//        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
//        
//        if (employeeOptional.isPresent()) {
//            // Return the email address if the employee is found
//            return employeeOptional.get().getEmail();
//        } else {
//            // Handle the case where the employee is not found
//            throw new RuntimeException("Employee not found with ID: " + employeeId);
//        }
//    }
//
//    public DialogueSession createSession(DialogueSession session) {
//    	session.setSessionId(sequenceGeneratorService.generateSequence(DialogueSession.class.getSimpleName()));
//        return repository.save(session);
//    }
//
//    public List<DialogueSession> getSessionsForEmployee(String employeeId) {
//        return repository.findByEmployeeId(employeeId);
//    }
//
//    public void sendEmailReminder(DialogueSession session) {
//        // Create a SimpleMailMessage object
//        SimpleMailMessage message = new SimpleMailMessage();
//        
//        // Set the email recipient
//        message.setTo(getEmployeeEmail(session.getEmployeeId()));
//        
//        // Set the email subject
//        message.setSubject("Reminder: Upcoming Meeting Scheduled on " + session.getNextMeetingDate());
//        
//        // Set the email body
//        String emailBody = String.format(
//                "Dear Employee, \n\n" +
//                "This is a reminder for your upcoming %s on %s.\n\n" +
//                "Meeting Details:\n" +
//                "Meeting Type: %s\n" +
//                "Review Date: %s\n" +
//                "Comments: %s\n\n" +
//                "Please be prepared and attend the meeting on time.\n\n" +
//                "Best Regards,\n" +
//                "Your HR Team",
//                session.getMeetingType(),
//                session.getNextMeetingDate(),
//                session.getMeetingType(),
//                session.getReviewDate(),
//                session.getCommentsAndNotes()
//        );
//        
//        message.setText(emailBody);
//        
//        // Send the email
//        emailSender.send(message);
//    }
//
//
//    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
//    public void checkForUpcomingMeetings() {
//        Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
//        List<DialogueSession> upcomingSessions = repository.findAll()
//            .stream()
//            .filter(session -> session.getNextMeetingDate().equals(tomorrow))
//            .collect(Collectors.toList());
//        
//        for (DialogueSession session : upcomingSessions) {
//            sendEmailReminder(session);
//        }
//    }
//
//    public DialogueSession updateSession(String employeeId, DialogueSession session) {
//        // Check if the session exists
//        Optional<DialogueSession> existingSessionOpt = repository.findById(employeeId);
//        if (!existingSessionOpt.isPresent()) {
//            // Handle the case where the session does not exist
//            throw new IllegalArgumentException("Dialogue session with ID " + employeeId + " not found");
//        }
//
//        // Get the existing session
//        DialogueSession existingSession = existingSessionOpt.get();
//
//        // Update fields
//        existingSession.setMeetingType(session.getMeetingType());
//        existingSession.setEmployeeId(session.getEmployeeId());
//        existingSession.setReviewDate(session.getReviewDate());
//        existingSession.setCommentsAndNotes(session.getCommentsAndNotes());
//        existingSession.setNextMeetingDate(session.getNextMeetingDate());
//
//        // Save the updated session
//        return repository.save(existingSession);
//    }
//
//    public boolean deleteSession(String employeeId) {
//        // Check if the session exists before attempting to delete
//        if (repository.existsById(employeeId)) {
//        	repository.deleteById(employeeId);
//            return true;
//        } else {
//            return false;
//        }
//    }
//    
//    public List<DialogueSession> getAllSessions() {
//        return repository.findAll();
//    }
//
//    public List<DialogueSession> getEmployeeSessions(String employeeId) {
//        return repository.findByEmployeeId(employeeId);
//    }
//
//    public boolean editSessionComment(String sessionId, String comment) {
//        // Find the session by ID
//        Optional<DialogueSession> optionalSession = repository.findById(sessionId);
//        
//        if (optionalSession.isPresent()) {
//            DialogueSession session = optionalSession.get();
//
//            // Check if the session is editable
//            if (session.isEditable()) {
//                session.setCommentsAndNotes(comment);   // Set the new comment
//                session.setEditable(false);    // Disable further edits
//                repository.save(session);   // Save the changes
//                return true;   // Indicate the edit was successful
//            }
//        }
//        return false;  // Return false if the session is not editable or doesn't exist
//    }
//}
//





package com.example.th.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.th.model.DialogueSession;
import com.example.th.model.Employee;
import com.example.th.repository.DialogueSessionRepository;
import com.example.th.repository.EmployeeRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DialogueSessionService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    
    @Autowired
    private DialogueSessionRepository repository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private JavaMailSender emailSender;

    private String getEmployeeEmail(String employeeId) {
        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        
        if (employeeOptional.isPresent()) {
            return employeeOptional.get().getEmail();
        } else {
            throw new RuntimeException("Employee not found with ID: " + employeeId);
        }
    }

    public DialogueSession createSession(DialogueSession session) {
        session.setSessionId(sequenceGeneratorService.generateSequence(DialogueSession.class.getSimpleName()));
        return repository.save(session);
    }

    public List<DialogueSession> getSessionsForEmployee(String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public void sendEmailReminder(DialogueSession session) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(getEmployeeEmail(session.getEmployeeId()));
        message.setSubject("Reminder: Upcoming Meeting Scheduled on " + session.getNextMeetingDate());
        
        String emailBody = String.format(
                "Dear Employee, \n\n" +
                "This is a reminder for your upcoming %s on %s.\n\n" +
                "Meeting Details:\n" +
                "Meeting Type: %s\n" +
                "Review Date: %s\n" +
                "Comments: %s\n\n" +
                "Please be prepared and attend the meeting on time.\n\n" +
                "Best Regards,\n" +
                "Your HR Team",
                session.getMeetingType(),
                session.getNextMeetingDate(),
                session.getMeetingType(),
                session.getReviewDate(),
                session.getCommentsAndNotes()
        );
        
        message.setText(emailBody);
        emailSender.send(message);
    }

    @Scheduled(cron = "0 0 9 * * ?") // Every day at 9 AM
    public void checkForUpcomingMeetings() {
        Date tomorrow = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        List<DialogueSession> upcomingSessions = repository.findAll()
            .stream()
            .filter(session -> session.getNextMeetingDate().equals(tomorrow))
            .collect(Collectors.toList());
        
        for (DialogueSession session : upcomingSessions) {
            sendEmailReminder(session);
        }
    }

//    public DialogueSession updateSession(String sessionId, DialogueSession session) {
//        DialogueSession existingSession = repository.findById(sessionId)
//                .orElseThrow(() -> new IllegalArgumentException("Dialogue session with ID " + sessionId + " not found"));
//
//        // Update fields
//        existingSession.setMeetingType(session.getMeetingType());
//        existingSession.setEmployeeId(session.getEmployeeId());
//        existingSession.setReviewDate(session.getReviewDate());
//        existingSession.setCommentsAndNotes(session.getCommentsAndNotes());
//        existingSession.setNextMeetingDate(session.getNextMeetingDate());
//
//        return repository.save(existingSession);
//    }
//    
    
    public DialogueSession updateSession(String sessionId, DialogueSession session) {
        // Fetch the existing session by sessionId (which is now a String)
        Optional<DialogueSession> existingSession = repository.findById(sessionId);
        if (existingSession.isPresent()) {
            // Update fields as necessary
            DialogueSession updatedSession = existingSession.get();
         //   updatedSession.setEmployeeId(session.getEmployeeId());
            updatedSession.setMeetingType(session.getMeetingType());
            updatedSession.setReviewDate(session.getReviewDate());
            updatedSession.setCommentsAndNotes(session.getCommentsAndNotes());
            updatedSession.setNextMeetingDate(session.getNextMeetingDate());
            updatedSession.setMeetingURL(session.getMeetingURL());
            return repository.save(updatedSession);
        } else {
            throw new IllegalArgumentException("Dialogue session with ID " + sessionId + " not found");
        }
    }

    


    public boolean deleteSession(String sessionId) {
        if (repository.existsById(sessionId)) {
            repository.deleteById(sessionId);
            return true;
        }
        return false;
    }
    
    public List<DialogueSession> getAllSessions() {
        return repository.findAll();
    }

    public boolean editSessionComment(String sessionId, String comment) {
        Optional<DialogueSession> optionalSession = repository.findById(sessionId);
        
        if (optionalSession.isPresent()) {
            DialogueSession session = optionalSession.get();

            if (session.isEditable()) {
                session.setCommentsAndNotes(comment);
                session.setEditable(false);
                repository.save(session);
                return true;
            }
        }
        return false;
    }

    public List<DialogueSession> getEmployeeSessions(String employeeId) {
    	return repository.findByEmployeeId(employeeId);
    }
    
    
}

