package com.example.th.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.th.model.Asset_Managenment;

import java.util.List;

public interface AssetRepository extends MongoRepository<Asset_Managenment, String> {

    // Find assets by employee ID
    List<Asset_Managenment> findByEmployeeId(String employeeId);

    // Find assets by type
    List<Asset_Managenment> findByAssetType(String assetType);
}