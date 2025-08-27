package com.aurionpro.authentication;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.catalina.connector.Response;

import com.aurionpro.Dao.AdminDao;
import com.aurionpro.Dao.CustomerDao;
import com.aurionpro.model.Admin;
import com.aurionpro.model.Customer;
import com.aurionpro.service.LoginService;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
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
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String role = request.getParameter("role");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		HttpSession session = request.getSession();

		if ("admin".equals(role)) {
			Admin admin = loginService.loginAdmin(email, password);
			if (admin != null) {
				session.setAttribute("role", "admin");
				session.setAttribute("admin", admin);
				response.sendRedirect(request.getContextPath()+"/adminControl?command=DASHBOARD");
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp?error=Invalid Admin Credentials");
				dispatcher.forward(request, response);
			}
		} else {
			Customer customer = loginService.loginCustomer(email, password);
			if (customer != null) {
				session.setAttribute("role", "customer");
				session.setAttribute("customer", customer);
				response.sendRedirect(request.getContextPath()+"/customerControl?command=DASHBOARD");
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/login.jsp?error=Invalid Customer Credentials");
				dispatcher.forward(request, response);
			}
		}
	}
}
