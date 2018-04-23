package com.optimum.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;
import com.optimumn.pojo.Employee;

@WebServlet("/EmployeeController")
public class EmployeeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private EmployeeDAO refEmployeeDAO;
	private Employee refEmployee;

	private RequestDispatcher refRequestDispatcher;
	
    public EmployeeController() {
    	refEmployeeDAO=new EmployeeDAOimpl(); 
		refEmployee =new Employee();      
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	//get data from html page
    	String email=request.getParameter("uid");
    	String password=request.getParameter("pass");

    	//set data to POJO class
    	refEmployee.setEmail(email);
    	refEmployee.setPassword(password);

    	//check if user logging in is an admin
    	if(refEmployeeDAO.loginAdmin(refEmployee)) {
    		//true 

    		//create session
    		HttpSession session=request.getSession();
    		session.setAttribute("refEmployeeJSP", refEmployee.getEmail());

    		//setting session to expire
    		session.setMaxInactiveInterval(15*60);//15 min

    		Cookie userName=new Cookie("refEmployeeJSP",email);
    		response.addCookie(userName);
    		String encodedURL=response.encodeRedirectURL("AdminLoginPage.jsp");
    		response.sendRedirect(encodedURL);

    		//check if user logging in is employee
    	} else if(refEmployeeDAO.checkStatus(email)) {

    		if(refEmployeeDAO.loginEmployee(email,password)) {

    			String name=refEmployeeDAO.getEmployeeName(email);

    			//create session
    			HttpSession session=request.getSession();
    			session.setAttribute("refEmployeeJSP", refEmployee.getEmail());
    			session.setAttribute("refEmployeeJSPName", name);

    			//setting session to expire
    			session.setMaxInactiveInterval(15*60);//15 min

    			Cookie userName=new Cookie("refEmployeeJSP",email);
    			response.addCookie(userName);
    			String encodedURL=response.encodeRedirectURL("EmployeeLogin.jsp");
    			response.sendRedirect(encodedURL);					
    		}
    		else {
    			//false
    			request.setAttribute("invalidError", "<font color='red'>Invalid User ID or Password.</font>");
    			refRequestDispatcher =getServletContext().getRequestDispatcher("/LoginPage.jsp");
    			refRequestDispatcher.include(request,response);
    		}
    		/*admin should never reach here(assume admin always remember)*/
    		/*if status is locked it will inform employee*/
    	}else if(!refEmployeeDAO.checkStatus(email)){
    		request.setAttribute("invalidError", "<font color='red'>Your account has been locked. Please contact the admin.</font>");
    		refRequestDispatcher =getServletContext().getRequestDispatcher("/LoginPage.jsp");
    		refRequestDispatcher.include(request,response);
    	}
    }//end of service

}//end of EmployeeController


