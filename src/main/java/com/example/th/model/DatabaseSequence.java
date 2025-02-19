package com.example.th.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
public class DatabaseSequence {

    @Id
    private String id;  // Identifier for the sequence (e.g., "Perk", "Asset", etc.)
    private String seq;   // The current sequence number

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

    
}
