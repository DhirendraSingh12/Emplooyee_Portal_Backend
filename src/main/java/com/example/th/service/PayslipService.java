package com.example.th.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.th.model.Payslip;
import com.example.th.repository.PayslipRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@Service
public class PayslipService {

    @Autowired
    private PayslipRepository payslipRepository;
    

    public List<Payslip> uploadPayslipsInBulk(MultipartFile file) throws IOException {
        // Implement logic to parse the file and save payslips
        // For simplicity, assuming a CSV file format here
        List<Payslip> payslips = parsePayslipsFromCSV(file);
        return payslipRepository.saveAll(payslips);
    }

	public List<Payslip> getPayslipsByEmployeeId(String employeeId) {
        return (List<Payslip>) payslipRepository.findByEmployeeId(employeeId);
    }

    // Method to parse payslips from CSV
    private List<Payslip> parsePayslipsFromCSV(MultipartFile file) throws IOException {
        List<Payslip> payslips = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows) {
                Payslip payslip = new Payslip();
                payslip.setEmployeeId(row[0]);
                payslip.setMonth(LocalDate.parse(row[1]));
               // payslip.setTotalSalary(Double.parseDouble(row[2]));
                payslip.setStatus(row[3]);
                payslips.add(payslip);
            }
        } catch (CsvException e) {
            throw new IOException("Error parsing CSV file", e);
        }
        return payslips;
    }

	public Payslip createSalarySlip(Payslip payslip) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Payslip> getAllSalarySlips() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Payslip savePayslip(Payslip payslip) {
        return payslipRepository.save(payslip);
    }

    public List<Payslip> findByEmployeeId(String employeeId) {
        return payslipRepository.findByEmployeeId(employeeId);
    }


}
