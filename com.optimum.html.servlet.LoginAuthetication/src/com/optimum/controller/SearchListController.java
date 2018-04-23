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


@WebServlet("/SearchListController")
public class SearchListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDAO refEmployeeDAO;
;
	ArrayList<String> list;
	
	private RequestDispatcher refRequestDispatcher;
	
    public SearchListController() {
    	refEmployeeDAO=new EmployeeDAOimpl(); //		
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get data from html page
		String search=request.getParameter("search"); 
		search=search.toUpperCase();
		String type=request.getParameter("type");
		
		list=refEmployeeDAO.searchEmployee(search, type);
					
		request.setAttribute("List", list);
		refRequestDispatcher =request.getRequestDispatcher("ViewEmployeeList.jsp");
		refRequestDispatcher.include(request,response);
					
	}

}
