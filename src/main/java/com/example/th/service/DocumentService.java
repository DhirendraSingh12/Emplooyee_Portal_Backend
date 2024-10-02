//package com.example.th.service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.model.My_Document;
//import com.example.th.model.Employee;
//import com.example.th.repository.DocumentRepository;
//import com.example.th.repository.EmployeeRepository;
//import com.example.th.exception.ResourceNotFoundException;
//
//@Service
//public class DocumentService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    // Create a new document for an employee
//    public My_Document createDocumentForEmployee(String employeeId, My_Document document) {
//        @SuppressWarnings("unused")
//		Employee employee = employeeRepository.findByEmployeeId(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
//        
//        document.setEmployeeId(employeeId);
//        document.setUploadStatus(false);
//        document.setVerifyStatus("Pending");
//        document.setUploadedDate(null);  // Uploaded date will be set when the employee uploads the document
//        document.setDocumentId(sequenceGeneratorService.generateSequence(My_Document.class.getSimpleName()));
//        
//        return documentRepository.save(document);
//    }
//
//    // Update the status of a document
//    public My_Document updateDocumentStatus(String documentId, boolean uploadedStatus, String verifyStatus) {
//        My_Document document = documentRepository.findById(documentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
//
//        document.setUploadStatus(uploadedStatus);
//        if (uploadedStatus) {
//            document.setUploadedDate(LocalDateTime.now());
//        }
//        document.setVerifyStatus(verifyStatus);
//        return documentRepository.save(document);
//    }
//
//    // Retrieve all documents for a specific employee
//    public List<My_Document> getDocumentsForEmployee(String employeeId) {
//        return documentRepository.findByEmployeeId(employeeId);
//    }
//
//    // Retrieve all documents
//    public List<My_Document> getAllDocuments() {
//        return documentRepository.findAll();
//    }
//
//    // Create a new document (can be used to create documents in general, not tied to a specific employee)
//    public My_Document createDocument(My_Document document) {
//        return documentRepository.save(document);
//    }
//
//    public My_Document uploadDocument(My_Document document) {
//        // Set additional properties such as upload status, etc.
//        document.setUploadStatus(true); // Mark as uploaded successfully
//        // Save the document information in the database
//        return documentRepository.save(document);
//    }
//    
//    public boolean approveDocument(String documentId) {
//        // Find the document by ID
//        My_Document document = documentRepository.findById(documentId).orElse(null);
//        
//        if (document != null && !document.isApproved()) {
//            // Set the document as approved
//            document.setApproved(true);
//            documentRepository.save(document);
//            return true;
//        }
//        return false;
//    }
//    
//    public boolean deleteDocument(String documentId) {
//        // Check if the document exists
//        if (documentRepository.existsById(documentId)) {
//            // Delete the document
//            documentRepository.deleteById(documentId);
//            return true;
//        }
//        return false;
//    }
//    
//    public boolean rejectDocument(String documentId) {
//        Optional<My_Document> optionalDocument = documentRepository.findById(documentId);
//        
//        if (optionalDocument.isPresent()) {
//            My_Document document = optionalDocument.get();
//            if (!"Rejected".equals(document.getVerifyStatus())) {
//                document.setVerifyStatus("Rejected");
//                documentRepository.save(document);
//                return true; // Document rejected
//            }
//            return false; // Document already rejected
//        }
//        
//        return false; // Document not found
//    }
//}



package com.example.th.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.model.My_Document;
import com.example.th.model.Employee;
import com.example.th.repository.DocumentRepository;
import com.example.th.repository.EmployeeRepository;
import com.example.th.exception.ResourceNotFoundException;

@Service
public class DocumentService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new document for an employee
    @SuppressWarnings("unused")
	public My_Document createDocumentForEmployee(String employeeId, My_Document document) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        document.setEmployeeId(employeeId);
        document.setUploadStatus(false);
        document.setVerifyStatus("Pending");
        document.setUploadedDate(null);  // Uploaded date will be set when the employee uploads the document
        document.setDocumentId(sequenceGeneratorService.generateSequence(My_Document.class.getSimpleName()));
        
        return documentRepository.save(document);
    }

    // Update a document by its ID
    public My_Document updateDocument(String documentId, My_Document updatedDocument) {
        My_Document existingDocument = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));

        // Update the existing document's fields with the new values
        existingDocument.setEmployeeId(updatedDocument.getEmployeeId());
        existingDocument.setUploadStatus(updatedDocument.isUploadStatus());
        existingDocument.setVerifyStatus(updatedDocument.getVerifyStatus());
        existingDocument.setUploadedDate(updatedDocument.getUploadedDate());
        existingDocument.setApproved(updatedDocument.isApproved());

        return documentRepository.save(existingDocument);
    }

    // Retrieve all documents for a specific employee
    public List<My_Document> getDocumentsForEmployee(String employeeId) {
        return documentRepository.findByEmployeeId(employeeId);
    }

    // Retrieve all documents
    public List<My_Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // Create a new document (can be used to create documents in general, not tied to a specific employee)
    public My_Document createDocument(My_Document document) {
    	document.setDocumentId(sequenceGeneratorService.generateSequence(My_Document.class.getSimpleName()));
        return documentRepository.save(document);
    }

    public My_Document uploadDocument(My_Document document) {
        // Set additional properties such as upload status, etc.
        document.setUploadStatus(true); // Mark as uploaded successfully
        // Save the document information in the database
        return documentRepository.save(document);
    }

    public boolean approveDocument(String documentId) {
        My_Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
        
        if (!document.isApproved()) {
            document.setApproved(true);
            documentRepository.save(document);
            return true;
        }
        return false;
    }

    public boolean deleteDocument(String documentId) {
        if (documentRepository.existsById(documentId)) {
            documentRepository.deleteById(documentId);
            return true;
        }
        return false;
    }

    public boolean rejectDocument(String documentId) {
        My_Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found with ID: " + documentId));
        
        if (!"Rejected".equals(document.getVerifyStatus())) {
            document.setVerifyStatus("Rejected");
            documentRepository.save(document);
            return true; // Document rejected
        }
        return false; // Document already rejected
    }
}

