//package com.example.th.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import com.example.th.exception.ResourceNotFoundException;
//import com.example.th.model.Asset_Managenment;
//import com.example.th.model.Employee;
//import com.example.th.repository.AssetRepository;
//import com.example.th.repository.EmployeeRepository;
//
//@Service
//
//public class AssetService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private AssetRepository assetRepository;
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//    
//    private static final Logger log = LoggerFactory.getLogger(AssetService.class);
//
//    // Assign an asset to an employee
//    public Asset_Managenment assignAssetToEmployee(String employeeId, Asset_Managenment asset) {
//        Optional<Employee> employee = employeeRepository.findByEmployeeId(employeeId);
//        if (employee.isPresent()) {
//            asset.setEmployeeId(employeeId);
//            return assetRepository.save(asset);
//        } else {
//            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
//        }
//    }
//
//    // Retrieve all assets for an employee
//    public List<Asset_Managenment> getAssetsForEmployee(String employeeId) {
//        return assetRepository.findByEmployeeId(employeeId);
//    }
//
//    // Update the status of an asset
//    public Asset_Managenment updateAssetStatus(String assetId, String status) {
//    	Asset_Managenment asset = assetRepository.findById(assetId)
//                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));
//
//        asset.setStatus(status);
//        return assetRepository.save(asset);
//    }
//
//    // Retrieve all assets
//    public List<Asset_Managenment> getAllAssets() {
//        return assetRepository.findAll();
//    }
//
//    // Add a new asset
//    public Asset_Managenment addAsset(Asset_Managenment asset) {
//    	asset.setAssetId(sequenceGeneratorService.generateSequence(Asset_Managenment.class.getSimpleName()));
//        return assetRepository.save(asset);
//    }
//
////    // Update an existing asset
////    public Asset_Managenment updateAsset(String employeeId, Asset_Managenment asset) {
////        if (assetRepository.existsById(employeeId)) {
////            asset.setEmployeeId(employeeId);
////            asset.setAssetType(asset.getAssetType());
////            return assetRepository.save(asset);
////        } else {
////            throw new ResourceNotFoundException("Asset not found with ID: " + employeeId); // Throw an exception if asset not found
////        }
////    }
//    
// // Update an existing asset
//    public Asset_Managenment updateAsset(String employeeId, Asset_Managenment asset) {
//        // Fetch the existing asset(s) by employeeId
//        List<Asset_Managenment> existingAssets = assetRepository.findByEmployeeId(employeeId);
//        
//        if (!existingAssets.isEmpty()) {
//            // Assuming you want to update the first asset found
//            Asset_Managenment assetToUpdate = existingAssets.get(0);
//            
//            // Update the necessary fields
//          //  assetToUpdate.setEmployeeId(asset.getEmployeeId());
//            assetToUpdate.setEmployeeName(asset.getEmployeeName());
//            assetToUpdate.setAssetType(asset.getAssetType());
//            assetToUpdate.setDateGiven(asset.getDateGiven());
//            assetToUpdate.setEstimatedValue(asset.getEstimatedValue());
//            assetToUpdate.setSerialNumber(asset.getSerialNumber());
//            assetToUpdate.setInsuranceDetails(asset.getInsuranceDetails());
//            assetToUpdate.setStatus(asset.getStatus());
//            
//            // Save and return the updated asset
//            return assetRepository.save(assetToUpdate);
//        } else {
//            throw new ResourceNotFoundException("Asset not found with Employee ID: " + employeeId);
//        }
//    }
//
//
//
// // Delete an asset
//    public void deleteAsset(String employeeId) {
//        if (assetRepository.existsById(employeeId)) {
//            log.info("Deleting asset with ID: {}", employeeId);
//            assetRepository.deleteById(employeeId);
//            log.info("Asset deleted successfully: {}", employeeId);
//        } else {
//            log.error("Asset not found with ID: {}", employeeId);
//            throw new ResourceNotFoundException("Asset not found with ID: " + employeeId);
//        }
//    }
//
//}





package com.example.th.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Asset_Managenment;
import com.example.th.repository.AssetRepository;

@Service
public class AssetService {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private AssetRepository assetRepository;

    private static final Logger log = LoggerFactory.getLogger(AssetService.class);

    // Assign an asset to an employee
    public Asset_Managenment assignAssetToEmployee(String employeeId, Asset_Managenment asset) {
        asset.setEmployeeId(employeeId);
        return assetRepository.save(asset);
    }

    // Retrieve all assets for an employee
    public List<Asset_Managenment> getAssetsForEmployee(String employeeId) {
        return assetRepository.findByEmployeeId(employeeId);
    }

    // Update the status of an asset
    public Asset_Managenment updateAssetStatus(String assetId, String status) {
        Asset_Managenment asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));

        asset.setStatus(status);
        return assetRepository.save(asset);
    }

    // Retrieve all assets
    public List<Asset_Managenment> getAllAssets() {
        return assetRepository.findAll();
    }

    // Add a new asset
    public Asset_Managenment addAsset(Asset_Managenment asset) {
        asset.setAssetId(sequenceGeneratorService.generateSequence(Asset_Managenment.class.getSimpleName()));
        return assetRepository.save(asset);
    }

    // Update an existing asset using assetId
    public Asset_Managenment updateAsset(String assetId, Asset_Managenment asset) {
        Asset_Managenment existingAsset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));

        // Update the necessary fields
        existingAsset.setEmployeeName(asset.getEmployeeName());
        existingAsset.setAssetType(asset.getAssetType());
        existingAsset.setDateGiven(asset.getDateGiven());
        existingAsset.setEstimatedValue(asset.getEstimatedValue());
        existingAsset.setSerialNumber(asset.getSerialNumber());
        existingAsset.setInsuranceDetails(asset.getInsuranceDetails());
        existingAsset.setStatus(asset.getStatus());

        // Save and return the updated asset
        return assetRepository.save(existingAsset);
    }

    // Delete an asset using assetId
    public void deleteAsset(String assetId) {
        if (assetRepository.existsById(assetId)) {
            log.info("Deleting asset with ID: {}", assetId);
            assetRepository.deleteById(assetId);
            log.info("Asset deleted successfully: {}", assetId);
        } else {
            log.error("Asset not found with ID: {}", assetId);
            throw new ResourceNotFoundException("Asset not found with ID: " + assetId);
        }
    }
}
