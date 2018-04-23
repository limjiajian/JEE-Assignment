package com.optimum.dao;

import java.util.ArrayList;

import com.optimumn.pojo.Employee;

public interface EmployeeDAO {

	//login page
	boolean loginAdmin(Employee refEmployee);

	boolean loginEmployee(String email, String pass);

	String createNewPassword(String email);
	
	boolean checkStatus(String email);
	
	//admin page
	boolean createNewEmployee(Employee ref);

	ArrayList<String> viewEmployeeList();
	
	void lockEmployee(String empID);

	ArrayList<String> searchEmployee(String search, String type);

	ArrayList<String> viewRequestStatus();

	void unlockEmployee(String empID);

	//employee page
	String getEmployeeName(String email);
	
	void updateProfilePic(Employee refEmp);

	void updateAddress(Employee ref);
	
	void updateQualification(Employee ref);

	void updateCertificate(Employee ref);

	void updateMobile(Employee ref);

	Employee viewProfile(String email);

	byte[] getImage(String email);

	String hashPassword(String password);

	

	


	

}
