//package com.example.th.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.exception.ResourceNotFoundException;
//import com.example.th.model.Employee;
//import com.example.th.model.Expense;
//import com.example.th.repository.EmployeeRepository;
//import com.example.th.repository.ExpenseRepository;
//
//import java.time.LocalDate;
//import java.time.temporal.TemporalAdjusters;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class ExpenseService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    // Create a new expense for an employee
//    public Expense createExpenseForEmployee(String employeeId, Expense expense) {
//        @SuppressWarnings("unused")
//		Employee employee = employeeRepository.findByEmployeeId(employeeId)
//            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
//        
//        expense.setEmployeeId(employeeId);
//        expense.setStatus("Pending"); // Default status for new expenses
//        
//        expense.setExpenseId(sequenceGeneratorService.generateSequence(Expense.class.getSimpleName()));
//        return expenseRepository.save(expense);
//    }
//
//    // Approve an expense
//    public boolean approveExpense(String expenseId) {
//        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
//        
//        if (expenseOptional.isPresent()) {
//            Expense expense = expenseOptional.get();
//            expense.setStatus("Approved");
//            expenseRepository.save(expense);
//            return true;
//        }
//        
//        return false;
//    }
//    
//    // Get expenses for a specific employee
//    public List<Expense> getExpensesForEmployee(String employeeId) {
//        return expenseRepository.findByEmployeeId(employeeId);
//    }
//
//    // Get all expenses
//    public List<Expense> getAllExpenses() {
//        return expenseRepository.findAll();
//    }
//
//    // Get expenses for an employee within a specific month
//    public List<Expense> getByEmployeeIdAndMonth(String empId, LocalDate month) {
//        LocalDate startOfMonth = month.withDayOfMonth(1);
//        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
//        return expenseRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
//    }
//
//    // Get expenses by status
//    public List<Expense> getExpensesByStatus(String status) {
//        return expenseRepository.findByStatus(status);
//    }
//    
//    public boolean approveExpensesByEmployeeId(String employeeId) {
//        List<Expense> expenses = expenseRepository.findByEmployeeId(employeeId);
//        
//        if (!expenses.isEmpty()) {
//            expenses.forEach(expense -> {
//                expense.setStatus("Approved");
//                expenseRepository.save(expense);
//            });
//            return true; // Expenses approved
//        }
//        
//        return false; // No expenses found for the employee
//    }
//
//    // Submit an expense
//    public Expense submitExpense(Expense expense) {
//        // Set default status or other fields as required before saving
//        if (expense.getStatus() == null) {
//            expense.setStatus("Pending");
//        }
//        return expenseRepository.save(expense);
//    }
//    
//    public boolean rejectExpense(String expenseId) {
//        Optional<Expense> optionalExpense = expenseRepository.findById(expenseId);
//        
//        if (optionalExpense.isPresent()) {
//            Expense expense = optionalExpense.get();
//            expense.setStatus("Rejected");
//            expenseRepository.save(expense);
//            return true; // Expense rejected
//        }
//        
//        return false; // Expense not found
//    }
//}

package com.example.th.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Employee;
import com.example.th.model.Expense;
import com.example.th.repository.EmployeeRepository;
import com.example.th.repository.ExpenseRepository;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExpenseService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create a new expense for an employee
    @SuppressWarnings("unused")
	public Expense createExpenseForEmployee(String employeeId, Expense expense) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
        
        expense.setEmployeeId(employeeId);
        expense.setStatus("Pending"); // Default status for new expenses
        expense.setExpenseId(sequenceGeneratorService.generateSequence(Expense.class.getSimpleName()));
        
        return expenseRepository.save(expense);
    }

    // Update an expense by its ID
    public Expense updateExpense(String expenseId, Expense updatedExpense) {
        Expense existingExpense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + expenseId));

        // Update fields of the existing expense
        existingExpense.setEmployeeId(updatedExpense.getEmployeeId());
        existingExpense.setAmount(updatedExpense.getAmount());
        existingExpense.setExpenseDescription(updatedExpense.getExpenseDescription());
        existingExpense.setExpenseDate(updatedExpense.getExpenseDate());
        existingExpense.setStatus(updatedExpense.getStatus());
        
        return expenseRepository.save(existingExpense);
    }

    // Approve an expense
    public boolean approveExpense(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + expenseId));

        expense.setStatus("Approved");
        expenseRepository.save(expense);
        return true;
    }
    
    // Get expenses for a specific employee
    public List<Expense> getExpensesForEmployee(String employeeId) {
        return expenseRepository.findByEmployeeId(employeeId);
    }

    // Get all expenses
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Get expenses for an employee within a specific month
    public List<Expense> getByEmployeeIdAndMonth(String empId, LocalDate month) {
        LocalDate startOfMonth = month.withDayOfMonth(1);
        LocalDate endOfMonth = month.with(TemporalAdjusters.lastDayOfMonth());
        return expenseRepository.findByEmployeeIdAndMonth(empId, startOfMonth, endOfMonth);
    }

    // Get expenses by status
    public List<Expense> getExpensesByStatus(String status) {
        return expenseRepository.findByStatus(status);
    }
    
    public boolean approveExpensesByEmployeeId(String employeeId) {
        List<Expense> expenses = expenseRepository.findByEmployeeId(employeeId);
        
        if (!expenses.isEmpty()) {
            expenses.forEach(expense -> {
                expense.setStatus("Approved");
                expenseRepository.save(expense);
            });
            return true; // Expenses approved
        }
        
        return false; // No expenses found for the employee
    }

    // Submit an expense
    public Expense submitExpense(Expense expense) {
        // Set default status or other fields as required before saving
        if (expense.getStatus() == null) {
            expense.setStatus("Pending");
            
        }
        expense.setExpenseId(sequenceGeneratorService.generateSequence(Expense.class.getSimpleName()));
        return expenseRepository.save(expense);
    }
    
    public boolean rejectExpense(String expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with ID: " + expenseId));

        expense.setStatus("Rejected");
        expenseRepository.save(expense);
        return true; // Expense rejected
    }

    // Delete an expense
    public boolean deleteExpense(String expenseId) {
        if (expenseRepository.existsById(expenseId)) {
            expenseRepository.deleteById(expenseId);
            return true;
        }
        return false; // Expense not found
    }
}

