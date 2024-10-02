package com.example.th.repository;

import com.example.th.model.Perk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PerkRepository extends MongoRepository<Perk, String> {

    // Find perks by type (internal/external)
    List<Perk> findByPerkType(String perkType);

    // Find perks by name
    List<Perk> findByPerkNameContaining(String perkName);
}