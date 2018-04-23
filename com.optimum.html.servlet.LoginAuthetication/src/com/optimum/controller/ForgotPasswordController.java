package com.optimum.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.connection.SendEmail;
import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;

@WebServlet("/ForgotPasswordController")
public class ForgotPasswordController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EmployeeDAO refEmployeeDAO;

	
	private RequestDispatcher refRequestDispatcher;

	 public ForgotPasswordController() {//create objects to be used
	    	refEmployeeDAO=new EmployeeDAOimpl();
	    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get data from html page
		String email=request.getParameter("email");	
		String name=refEmployeeDAO.getEmployeeName(email);
		String newPass=refEmployeeDAO.createNewPassword(email);
		if(newPass!=null&&name!=null) {
			new SendEmail().forgotMail(name, newPass, email);
			request.setAttribute("success", "Successfully Submitted to Email Address!");
			refRequestDispatcher =request.getRequestDispatcher("LoginPage.jsp");
			refRequestDispatcher.include(request,response);
		}else if(newPass==null||name==null) {
			request.setAttribute("failure", "<font color='red'>Email cannot be found...</font>");
			refRequestDispatcher =request.getRequestDispatcher("LoginPage.jsp");
			refRequestDispatcher.include(request,response);
		}
		
	}

}
