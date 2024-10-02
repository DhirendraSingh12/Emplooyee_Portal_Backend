//package com.example.th.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.th.model.Perk;
//import com.example.th.repository.PerkRepository;
//
//@Service
//public class PerkService {
//	
//	@Autowired
//    private SequenceGeneratorService sequenceGeneratorService;
//
//    @Autowired
//    private PerkRepository perkRepository;
//
//    public Perk createPerk(Perk perk) {
//    	perk.setPerkId(sequenceGeneratorService.generateSequence(Perk.class.getSimpleName()));
//        return perkRepository.save(perk);
//    }
//
//    public List<Perk> getAllPerks() {
//        return perkRepository.findAll();
//    }
//    
//    public boolean deletePerk(String perkId) {
//        // Check if the perk exists
//        if (perkRepository.existsById(perkId)) {
//            // Delete the perk
//            perkRepository.deleteById(perkId);
//            return true;
//        }
//        return false;
//    }
//}


package com.example.th.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.th.exception.ResourceNotFoundException;
import com.example.th.model.Perk;
import com.example.th.repository.PerkRepository;

@Service
public class PerkService {
    
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private PerkRepository perkRepository;

    // Create a new perk
    public Perk createPerk(Perk perk) {
        perk.setPerkId(sequenceGeneratorService.generateSequence(Perk.class.getSimpleName()));
        return perkRepository.save(perk);
    }

    // Get all perks
    public List<Perk> getAllPerks() {
        return perkRepository.findAll();
    }
    
    // Get a perk by its ID
    public Perk getPerkById(String perkId) {
        return perkRepository.findById(perkId)
            .orElseThrow(() -> new ResourceNotFoundException("Perk not found with ID: " + perkId));
    }

    // Update a perk
    public Perk updatePerk(String perkId, Perk updatedPerk) {
        Perk existingPerk = perkRepository.findById(perkId)
            .orElseThrow(() -> new ResourceNotFoundException("Perk not found with ID: " + perkId));
        
        // Update fields of the existing perk
        existingPerk.setPerkName(updatedPerk.getPerkName());
        existingPerk.setDescription(updatedPerk.getDescription());
        // Add other fields as needed
        
        return perkRepository.save(existingPerk);
    }

    // Delete a perk
    public boolean deletePerk(String perkId) {
        if (perkRepository.existsById(perkId)) {
            perkRepository.deleteById(perkId);
            return true;
        }
        return false; // Perk not found
    }
}
