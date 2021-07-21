package com.motorpool.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.motorpool.dao.RidesDAO;
import com.motorpool.dao.UsersDAO;
import com.motorpool.utils.DBUtils;

@WebServlet(urlPatterns = { "/AdminServlet", "/viewrides", "/updatepercentagepage", "/updatepercentage",
		"/viewallusers", "/viewnewusers","/approveuser","/activateuser","/deactivateuser" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();

		if (request.getRequestURI().equalsIgnoreCase("/MotorPool/viewrides")) {
			List<RidesDAO> ridelist = DBUtils.fetchAllRides();
			request.setAttribute("ridelist", ridelist);
			request.getRequestDispatcher("adminviewrides.jsp").forward(request, response);
		} else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/updatepercentagepage")) {
			float percentage = DBUtils.fetchPercentage();
			request.setAttribute("percentage", percentage);
			request.getRequestDispatcher("admincommission.jsp").forward(request, response);
		} else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/viewnewusers")) {
			List<UsersDAO> newusers = DBUtils.fetchAllUsers(true);
			if( request.getAttribute("flag")!=null)
			request.setAttribute("flag", request.getAttribute("flag"));
			
			request.setAttribute("users", newusers);
			request.getRequestDispatcher("adminregrequests.jsp").forward(request, response);
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/approveuser")) {
			boolean flag = DBUtils.activateUser(Integer.parseInt(request.getParameter("userid")));
			request.setAttribute("flag", flag);
			request.getRequestDispatcher("viewnewusers").forward(request, response);
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/activateuser")) {
			 DBUtils.activateUser(Integer.parseInt(request.getParameter("userid")));
			request.setAttribute("flag", "User Activated! A Mail has been sent to user.");
			request.getRequestDispatcher("viewallusers").forward(request, response);
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/deactivateuser")) {
			DBUtils.deactivateUser(Integer.parseInt(request.getParameter("userid")));
			request.setAttribute("flag", "User has been Deactivated!");
			request.getRequestDispatcher("viewallusers").forward(request, response);
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/viewallusers")) {
			List<UsersDAO> userslist = DBUtils.fetchAllUsers(false);
			if(request.getAttribute("flag")!=null)
				request.setAttribute("flag", request.getAttribute("flag"));
			
			request.setAttribute("users", userslist);
			request.getRequestDispatcher("adminviewusers.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getRequestURI().equalsIgnoreCase("/MotorPool/updatepercentage")) {
			boolean result = DBUtils.updatePercentage(Float.parseFloat(request.getParameter("percentage")));
			request.setAttribute("update", result);
			float percentage = DBUtils.fetchPercentage();
			request.setAttribute("percentage", percentage);
			request.getRequestDispatcher("admincommission.jsp").forward(request, response);
		}

	}

}
