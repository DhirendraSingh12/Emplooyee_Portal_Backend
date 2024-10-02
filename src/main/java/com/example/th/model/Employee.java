//package com.example.th.model;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Document(collection = "employees")
//public class Employee {
//
//	
//	
//    @Id
//    private String id;
//    
//    @Indexed(unique = true)
//	private String employeeId;   // Auto-generated ID (e.g., HFX0001)
//    private String employeeName;
//    //private String lastName;
//    private String gender;
//    private String mobile;
//    private String password;   // This can be saved directly or encrypted, depending on your needs
//    
//    @Transient 
//    private String rawPassword;
//    
//    private String designation;
//    private String department;
//    private String address;
//    private String email;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
//    private LocalDate dob;
//    private String education;
//    private String photo;   // Assuming photo is stored as a URL or file path
//
//	//private String role;
//    @JsonIgnore
//	private Collection<? extends GrantedAuthority> authorities;
//    
//	public Employee() {
//		super();
//	}
//
//
//	public Employee( String id, String employeeId, String employeeName,  String gender, String mobile,String rawPassword,
//            String password, String designation, String department, String address, String email, LocalDate dob,
//			String education, String photo) {
//		super();
//		this.id = id;
//		this.employeeId = employeeId;
//		this.employeeName = employeeName;
//		//this.lastName = lastName;
//		this.gender = gender;
//		this.mobile = mobile;
//		this.rawPassword = rawPassword;
//		this.password = password;
//		this.designation = designation;
//		this.department = department;
//		this.address = address;
//		this.email = email;
//		this.dob = dob;
//		this.education = education;
//		this.photo = photo;
//	}
//
//
//	public String getId() {
//		return id;
//	}
//
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//
//	public String getEmployeeId() {
//		return employeeId;
//	}
//
//
//	public void setEmployeeId(String employeeId) {
//		this.employeeId = employeeId;
//	}
//	
//	
//
//
////	public String getFirstName() {
////		return firstName;
////	}
////
////
////	public void setFirstName(String firstName) {
////		this.firstName = firstName;
////	}
////
////
////	public String getLastName() {
////		return lastName;
////	}
////
////
////	public void setLastName(String lastName) {
////		this.lastName = lastName;
////	}
//	
//	
//
//
//	public String getEmployeeName() {
//		return employeeName;
//	}
//
//
//	public String getRawPassword() {
//		return rawPassword;
//	}
//
//
//	public void setRawPassword(String rawPassword) {
//		this.rawPassword = rawPassword;
//	}
//
//
//	public void setEmployeeName(String employeeName) {
//		this.employeeName = employeeName;
//	}
//
//
//	public String getGender() {
//		return gender;
//	}
//
//
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//
//
//	public String getMobile() {
//		return mobile;
//	}
//
//
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//	public String getDesignation() {
//		return designation;
//	}
//
//
//	public void setDesignation(String designation) {
//		this.designation = designation;
//	}
//
//
//	public String getDepartment() {
//		return department;
//	}
//
//
//	public void setDepartment(String department) {
//		this.department = department;
//	}
//
//
//	public String getAddress() {
//		return address;
//	}
//
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//
//	public String getEmail() {
//		return email;
//	}
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//	public LocalDate getDob() {
//		return dob;
//	}
//
//
//	public void setDob(LocalDate dob) {
//		this.dob = dob;
//	}
//
//
//	public String getEducation() {
//		return education;
//	}
//
//
//	public void setEducation(String education) {
//		this.education = education;
//	}
//
//
//	public String getPhoto() {
//		return photo;
//	}
//
//
//	public void setPhoto(String photo) {
//		this.photo = photo;
//	}
//
//
//	public List<String> getAuthorityNames() {
//	    if (authorities == null) {
//	        return Collections.emptyList();
//	    }
//	    return authorities.stream().map(Authority::getName).collect(Collectors.toList());
//	}
//    
//	
//}
//    
//    
//    



package com.example.th.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "employees")
public class Employee {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String employeeId;   // Auto-generated ID (e.g., HFX0001)
    private String employeeName;
    private String gender;
    private String mobile;
    private String password;   // This can be saved directly or encrypted, depending on your needs
    
    @Transient 
    private String rawPassword;
    
    private String designation;
    private String department;
    private String address;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
    
    private String education;
    private String photo;   // Assuming photo is stored as a URL or file path

    // Authorities for Spring Security
    @JsonIgnore
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();  // Initialize to avoid null
    
    public Employee() {
        super();
    }

    public Employee(String id, String employeeId, String employeeName, String gender, String mobile, String rawPassword,
                    String password, String designation, String department, String address, String email, LocalDate dob,
                    String education, String photo) {
        super();
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.gender = gender;
        this.mobile = mobile;
        this.rawPassword = rawPassword;
        this.password = password;
        this.designation = designation;
        this.department = department;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.education = education;
        this.photo = photo;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
    }

    // Authorities Management
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return Collections.emptyList();
        }
        return authorities;
    }

    public void setAuthorities(List<String> authorityNames) {
        this.authorities = authorityNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Get the list of authority names for DTO-like use in JSON
    public List<String> getAuthorityNames() {
        if (authorities == null) {
            return Collections.emptyList();
        }
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

}

//    