package com.example.th.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.th.model.Employee;
import com.example.th.model.Timesheet;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPendingTimesheetEmail(String employeeId, List<Timesheet> pendingTimesheets) {
        // Fetch employee details (e.g., email) using employeeId
        String toEmail = getEmailForEmployee(employeeId);

        // Prepare email content
        String subject = "Pending Timesheets Reminder";
        String body = "You have pending timesheets from the last 5 days that need your attention.";

        // Send the email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    private String getEmailForEmployee(String employeeId) {
        // Fetch email from the employee repository or service
        return "employee@example.com"; // Replace with actual logic
    }
    
//    public void sendEmployeeCredentials(Employee employee) {
//        String subject = "Your Employee Credentials";
//        String body = String.format("Dear %s %s, \n\nYour Employee ID: %s \nYour Password: %s \n\nPlease keep this information secure.",
//                employee.getFirstName(), employee.getLastName(), employee.getEmployeeId(), employee.getPassword());
//
//        sendEmail(employee.getEmail(), subject, body);
//    }
    
    public void sendEmployeeCredentials(Employee employee) {
        // Create a new SimpleMailMessage object
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Set the recipient, subject, and text of the email
        message.setTo(employee.getEmail());
        message.setSubject("Your Login Crendential!");

        // Prepare the email body with employee details
        String emailBody = String.format(
            "Dear %s ,\n\n" +
            "We are pleased to welcome you to the company. Below are your login details:\n\n" +
            "Employee ID: %s\n\n" +
            "Email: %s\n\n" +
            "Password: %s\n\n" +
            "Please keep this information secure.\n\n" +
            "Best regards,\n" +
            "hireflex247 Pvt Ltd",
            employee.getEmployeeName(),  // Adjust if necessary
            employee.getEmployeeId(),
            employee.getEmail(),
            employee.getRawPassword() // This should be the raw password or a message if using encrypted passwords
        );

        // Set the text of the email
        message.setText(emailBody);

        // Send the email
        mailSender.send(message);
    }

    public void sendEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }

    public void sendEmployeeCredentials(String to, String employeeId, String rawPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your Employee Account Credentials");
        message.setText("Your Employee ID: " + employeeId + "\nYour Password: " + rawPassword);

        mailSender.send(message);
    }
}