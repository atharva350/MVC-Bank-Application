package com.aurionpro.controller;

import java.io.IOException;

import javax.sql.DataSource;

import com.aurionpro.Dao.AdminDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.service.LoginService;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/controller")
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private LoginService loginService;

	@Resource(name = "jdbc/db-source")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			AdminDao adminDao = new AdminDao(dataSource);
			CustomerDao customerDao = new CustomerDao(dataSource);
			loginService = new LoginService(adminDao, customerDao);
		} catch (Exception exc) {
			throw new ServletException(exc);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			// User is not logged in, redirect to login page
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp");
			dispatcher.forward(request, response);
		}
	}
}
