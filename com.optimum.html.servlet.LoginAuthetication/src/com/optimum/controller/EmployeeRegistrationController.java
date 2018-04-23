package com.optimum.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.optimum.connection.SendEmail;
import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;
import com.optimumn.pojo.Employee;

@WebServlet("/EmployeeRegistrationController")
//jsp can write java code while html cannot

@MultipartConfig(maxFileSize=1024*1024*5)
public class EmployeeRegistrationController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
       
	private EmployeeDAO refEmployeeDAO;
	private Employee refEmployee;
	
	private RequestDispatcher refRequestDispatcher;
	
    public EmployeeRegistrationController() {//create objects to be used
    	refEmployeeDAO=new EmployeeDAOimpl();
		refEmployee =new Employee();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//get data from html page
		String fullName=request.getParameter("fname");
		fullName=fullName.toUpperCase();
		
		String nric=request.getParameter("nric");
		String first = nric.substring(0, 1).toUpperCase();
		String last = nric.substring((nric.length() - 1), nric.length()).toUpperCase();
		String middle = nric.substring(1, nric.length() - 1);
		nric = first + middle + last;
		
		String gender=request.getParameter("gender");
		gender=gender.toUpperCase();
		
		String doB=request.getParameter("doB");

		String address=request.getParameter("address");
		address=address.toUpperCase();
		
		String country=request.getParameter("country");
		country=country.toUpperCase();		
		
		String qualification=request.getParameter("qualification");
		qualification=qualification.toUpperCase();
		
		//cert
		Part certificate=request.getPart("certificate");
		
		String department=request.getParameter("department");
		department=department.toUpperCase();
		
		String email=request.getParameter("email");		
		String mobile=request.getParameter("mobile");
		String employeeID=request.getParameter("empID");

	

		//set data to POJO class
		refEmployee.setFullName(fullName);
		refEmployee.setNric(nric);
		refEmployee.setGender(gender);
		refEmployee.setDateOfBirth(doB);
		refEmployee.setAddress(address);
		refEmployee.setCountry(country);
		refEmployee.setQualification(qualification);
		//cert
		refEmployee.setCertificate(certificate);
		refEmployee.setDepartment(department);
		refEmployee.setEmail(email);
		refEmployee.setMobile(mobile);
		refEmployee.setEmpID(employeeID);
		
		//create temp password
		String firstHalf = nric.substring(1, 5);
		String lastHalf = mobile.substring(4, 8);
		String password=firstHalf + lastHalf;
		refEmployee.setPassword(password);
		
		
			if(refEmployeeDAO.createNewEmployee(refEmployee)) {
				//false means Exception is caught in DAO
				request.setAttribute("errorAlreadyIn", "<font color='red'>Employee is already in the database or problem encountered uploading file...</font>");
				refRequestDispatcher =request.getRequestDispatcher("CreateNewEmployee.jsp");
				refRequestDispatcher.include(request,response);
			}else {
				//true if Exception is not caught in DAO
				request.setAttribute("successRegister", "<font color='red'>Successfully Registered new Employee!</font>");
				new SendEmail().registerMail(refEmployee);
				refRequestDispatcher =request.getRequestDispatcher("CreateNewEmployee.jsp");
				refRequestDispatcher.include(request,response);
			}
		} 
	
}
