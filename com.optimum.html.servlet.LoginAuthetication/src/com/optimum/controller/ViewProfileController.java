package com.optimum.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;
import com.optimumn.pojo.Employee;


@WebServlet("/ViewProfileController")
public class ViewProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EmployeeDAO refEmployeeDAO;
	private Employee refEmployee;

	private RequestDispatcher refRequestDispatcher;
    
    public ViewProfileController() {      
    	refEmployeeDAO=new EmployeeDAOimpl(); 
		refEmployee =new Employee();   
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("refEmployeeJSP");
		
		refEmployee=refEmployeeDAO.viewProfile(email);
				
		request.setAttribute("refEmployee", refEmployee);
		refRequestDispatcher =request.getRequestDispatcher("ViewProfile.jsp");
		refRequestDispatcher.include(request,response);
		
		
	}



}
