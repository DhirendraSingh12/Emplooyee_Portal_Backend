//package com.example.th.DTO;
//
//import java.util.List;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.th.model.Employee;
//
//public class EmployeeDTO {
//    private String employeeName;
////    private String lastName;
//    private String gender;
//    private String mobile;
//    private String password;
//    private String rawPassword;
//    private String designation;
//    private String department;
//    private String address;
//    private String email;
//    private String dob; // in String format, will be converted to LocalDate
//    private String education;
//    private MultipartFile photo; // to hold the file
//    private List<String> authorityNames;
//    
//    
//    
//    
//    
////	public String getFirstName() {
////		return firstName;
////	}
////	public void setFirstName(String firstName) {
////		this.firstName = firstName;
////	}
////	public String getLastName() {
////		return lastName;
////	}
////	public void setLastName(String lastName) {
////		this.lastName = lastName;
////	}
//    
//    public List<String> getAuthorityNames() {
//        return authorityNames;
//    }
//
//    public void setAuthorityNames(List<String> authorityNames) {
//        this.authorityNames = authorityNames;
//    }
//    
//	public String getGender() {
//		return gender;
//	}
//	public String getEmployeeName() {
//		return employeeName;
//	}
//	public void setEmployeeName(String employeeName) {
//		this.employeeName = employeeName;
//	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
//	public String getMobile() {
//		return mobile;
//	}
//	public void setMobile(String mobile) {
//		this.mobile = mobile;
//	}
//	public String getPassword() {
//		return password;
//	}
//	public void setPassword(String password) {
//		this.password = password;
//	}
//	public String getDesignation() {
//		return designation;
//	}
//	public void setDesignation(String designation) {
//		this.designation = designation;
//	}
//	public String getDepartment() {
//		return department;
//	}
//	public void setDepartment(String department) {
//		this.department = department;
//	}
//	public String getAddress() {
//		return address;
//	}
//	public void setAddress(String address) {
//		this.address = address;
//	}
//	public String getEmail() {
//		return email;
//	}
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	public String getDob() {
//		return dob;
//	}
//	public void setDob(String dob) {
//		this.dob = dob;
//	}
//	public String getEducation() {
//		return education;
//	}
//	public void setEducation(String education) {
//		this.education = education;
//	}
//	public MultipartFile getPhoto() {
//		return photo;
//	}
//	public void setPhoto(MultipartFile photo) {
//		this.photo = photo;
//	}
//	public String getRawPassword() {
//		return rawPassword;
//	}
//	public void setRawPassword(String rawPassword) {
//		this.rawPassword = rawPassword;
//	}
//	
//	
//    
//}


package com.example.th.DTO;

import java.time.LocalDate;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class EmployeeDTO {

    private String employeeName;
    private String gender;
    private String mobile;
    private String password;
    private String rawPassword;
    private String designation;
    private String department;
    private String address;
    private String email;
    private String dob; // in String format, will be converted to LocalDate
    private String education;
    private MultipartFile photo; // to hold the file
    private List<String> authorityNames;

    // Default constructor
    public EmployeeDTO() {}

    // Constructor with fields
    public EmployeeDTO(String employeeName, String gender, String mobile, String password, String rawPassword,
                       String designation, String department, String address, String email, String dob, 
                       String education, MultipartFile photo, List<String> authorityNames) {
        this.employeeName = employeeName;
        this.gender = gender;
        this.mobile = mobile;
        this.password = password;
        this.rawPassword = rawPassword;
        this.designation = designation;
        this.department = department;
        this.address = address;
        this.email = email;
        this.dob = dob;
        this.education = education;
        this.photo = photo;
        this.authorityNames = authorityNames;
    }

    // Getters and Setters
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

    public String getRawPassword() {
        return rawPassword;
    }

    public void setRawPassword(String rawPassword) {
        this.rawPassword = rawPassword;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public MultipartFile getPhoto() {
        return photo;
    }

    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }

    public List<String> getAuthorityNames() {
        return authorityNames;
    }

    public void setAuthorityNames(List<String> authorityNames) {
        this.authorityNames = authorityNames;
    }

}

