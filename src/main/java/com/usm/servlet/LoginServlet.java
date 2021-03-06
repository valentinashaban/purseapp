package com.usm.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.usm.config.Factory;
import com.usm.model.User;
import com.usm.service.UserService;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().removeAttribute("error");
		UserService.loadPage(request, response, "login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("ulogin");
		String password = request.getParameter("upassword");

		String page = "login.jsp";

		Factory factory = Factory.getInstance();
		User user = factory.getUserDao().findByLogin(login);

		if (null != user && user.getPassword().equals(password)) {
			request.getSession().setAttribute("user", user);
			page = "profile.jsp";
		} 
		else
			request.getSession().setAttribute("error", "User doesn't exist or password is wrong");
		response.sendRedirect(page);
	}

}
