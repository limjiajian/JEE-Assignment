package com.optimum.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;

@WebServlet("/ViewListController")
public class ViewListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private EmployeeDAO refEmployeeDAO;
	ArrayList<String> list;

	private RequestDispatcher refRequestDispatcher;
	
    public ViewListController() {
    	refEmployeeDAO=new EmployeeDAOimpl(); //		
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String empID="";
		empID=request.getParameter("lock"); 
		if(empID!=null&&!empID.equals("")) {
		refEmployeeDAO.lockEmployee(empID);
		}
		list=refEmployeeDAO.viewEmployeeList();
		request.setAttribute("List", list);
		refRequestDispatcher =request.getRequestDispatcher("ViewEmployeeList.jsp");
		refRequestDispatcher.include(request,response);
						
		
	}

}
