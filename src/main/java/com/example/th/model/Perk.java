package com.example.th.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "perks")
public class Perk {

    
	
	//private String id;
	
	@Id
	private String perkId;
    private String perkName;
    private String perkType; // internal or external
    private String URL; 
    private String description;
    
    
   // Getters and setters
    
    
	
	public String getPerkName() {
		return perkName;
	}
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	
	public String getPerkId() {
		return perkId;
	}
	public void setPerkId(String perkId) {
		this.perkId = perkId;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public void setPerkName(String perkName) {
		this.perkName = perkName;
	}
	public String getPerkType() {
		return perkType;
	}
	public void setPerkType(String perkType) {
		this.perkType = perkType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

    
}
