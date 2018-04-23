package com.optimumn.pojo;

import javax.servlet.http.Part;

public class Employee {

	private String fullName;
	private String nric;
	private String gender;
	private String dateOfBirth;
	private String address;
	private String country;
	private String qualification;
	private Part certificate;
	private String department;	
	private String email;
	private String mobile;
	private String empID;

	private String password;
	private String role;
	private int status;
	private int attempts;
	private Part profilePicture;
	
	
	public Employee() {
		this.role = "User";
		this.status = 0;
		this.attempts = 0;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getNric() {
		return nric;
	}
	public void setNric(String nric) {
		this.nric = nric;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public Part getCertificate() {
		return certificate;
	}

	public void setCertificate(Part certificate) {
		this.certificate = certificate;
	}	
	
	public Part getProfilePicture() {
		return profilePicture;
	}
	
	public void setProfilePicture(Part profilePicture) {
		this.profilePicture = profilePicture;
	}

}
