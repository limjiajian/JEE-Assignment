package com.optimum.controller;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;
import com.optimumn.pojo.Employee;

@WebServlet("/UpdateProfileController")

@MultipartConfig(maxFileSize=1024*1024*5)
public class UpdateProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EmployeeDAO refEmployeeDAO;
	private Employee refEmployee;

	private RequestDispatcher refRequestDispatcher;

    public UpdateProfileController() {
    	refEmployeeDAO=new EmployeeDAOimpl(); 
		refEmployee =new Employee();       
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("refEmployeeJSP");
		
		//get data from html page
		Part profilePic=request.getPart("profilePic");
		String profilePicFileName=Paths.get(profilePic.getSubmittedFileName()).getFileName().toString();
		
		String address=request.getParameter("address");
		address=address.toUpperCase();
		
		String qualification=request.getParameter("qualification");
		qualification=qualification.toUpperCase();
		
		Part certificate=request.getPart("certificate");
		String certificateFileName=Paths.get(certificate.getSubmittedFileName()).getFileName().toString();
		
		String mobile=request.getParameter("mobile");
		
		//set data to POJO class
		refEmployee.setEmail(email);
		refEmployee.setProfilePicture(profilePic);
		refEmployee.setAddress(address);
		refEmployee.setQualification(qualification);
		refEmployee.setCertificate(certificate);
		refEmployee.setMobile(mobile);
		
		//check if field is not empty then update
		if(!profilePicFileName.isEmpty()) {
			refEmployeeDAO.updateProfilePic(refEmployee);
		}		
		if(!address.isEmpty()) {
			refEmployeeDAO.updateAddress(refEmployee);
		}		
		if(!qualification.isEmpty()) {
			refEmployeeDAO.updateQualification(refEmployee);
		}		
		if(!certificateFileName.isEmpty()) {
			refEmployeeDAO.updateCertificate(refEmployee);
		}		
		if(!mobile.isEmpty()) {
			refEmployeeDAO.updateMobile(refEmployee);
		}
		request.setAttribute("successUpdate", "<font color='red'>Successfully Updated your profile!</font>");
		refRequestDispatcher =request.getRequestDispatcher("UpdateProfile.jsp");
		refRequestDispatcher.include(request,response);
	}//end of service

}
