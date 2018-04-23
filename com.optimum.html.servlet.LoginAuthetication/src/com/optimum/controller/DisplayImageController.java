package com.optimum.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.optimum.dao.EmployeeDAO;
import com.optimum.dao.EmployeeDAOimpl;


@WebServlet("/DisplayImageController")
public class DisplayImageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private EmployeeDAO refEmployeeDAO;

    public DisplayImageController() {
    	refEmployeeDAO=new EmployeeDAOimpl();   
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("refEmployeeJSP");
		
		OutputStream img;
		
		// getting the image file and printing it to the screen 
		byte[] profilePic = refEmployeeDAO.getImage(email);
		response.setContentType("image/png");
		img = response.getOutputStream();
		img.write(profilePic);
		img.flush();
		img.close();
	}

}
