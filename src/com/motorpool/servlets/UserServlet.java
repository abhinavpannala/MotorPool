package com.motorpool.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.motorpool.dao.RidesDAO;
import com.motorpool.dao.UsersDAO;
import com.motorpool.utils.DBUtils;
import com.motorpool.utils.DateConverter;

@WebServlet(urlPatterns = { "/registeruser", "/setridepage", "/setride", "/ridepage", "/startride", "/endride",
							"/searchride","/joinride","/cancelride","/updatevehicle","/viewridesoffered","/viewridestaken",
							"/addpoints","/updatevehicletype","/changepassword"
							})
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();

		if (request.getRequestURI().equalsIgnoreCase("/MotorPool/setridepage")) {
			int userid = (int) session.getAttribute("userid");
			boolean allowed = false;
			
			if(request.getAttribute("ridestatus")!=null)
				request.setAttribute("ridestatus", request.getAttribute("ridestatus"));
				
			if (request.getParameter("ridetype").equalsIgnoreCase("car")) {
				allowed = DBUtils.checkVehicle(request.getParameter("ridetype"), userid);
				request.setAttribute("allowed", allowed);
				request.getRequestDispatcher("useroffercarride.jsp").forward(request, response);

			}
		} 
		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/ridepage")) {
			List<RidesDAO> ridelist = DBUtils.fetchRides((int)session.getAttribute("userid"));
			if(request.getAttribute("ridestatus")!= null) {
				request.setAttribute("ridestatus", request.getAttribute("ridestatus"));
			}
			request.setAttribute("ridelist", ridelist);
			request.getRequestDispatcher("userstartendrides.jsp").forward(request, response);
			//response.sendRedirect("userstartendrides.jsp");
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/updatevehicle")) {
			UsersDAO user = DBUtils.fetchUser((int)session.getAttribute("userid"));
			request.setAttribute("user", user);
			if(request.getAttribute("flag")!=null)
			request.setAttribute("flag", request.getAttribute("flag"));
			
			if(request.getParameter("ridetype").equalsIgnoreCase("car"))
				request.getRequestDispatcher("useraddcar.jsp").forward(request, response);
		} 		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/endride")) {
			String status = DBUtils.fetchStatus(Integer.parseInt(request.getParameter("rideid")));			
			String ridestatus="";
			boolean statusflag=true;
			
			//validation							
			if(!(status.equalsIgnoreCase("Progress"))) {
					statusflag = false;
					ridestatus = "Cannot end ride!";
				}			
			if(statusflag) {
				DBUtils.endride((int)session.getAttribute("userid"),Integer.parseInt(request.getParameter("rideid")));
				ridestatus = "Your ride has ended!";
			}
			
			request.setAttribute("ridestatus", ridestatus);
			request.getRequestDispatcher("ridepage").forward(request, response);			
		} 
		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/startride")) {
			List<RidesDAO> ridelist = DBUtils.fetchRideStatuses((int)session.getAttribute("userid"));
			int rideid = Integer.parseInt(request.getParameter("rideid"));
			String ridestatus="";
			boolean statusflag=true;
			
			//validation
			for(RidesDAO rides: ridelist) {
				if(rides.getRideid() == rideid) {
					if(!(rides.getStatus().equalsIgnoreCase("Created"))) {
						statusflag = false;
						ridestatus = "Cannot start ride!";
					}
				}				
				if(rides.getStatus().equalsIgnoreCase("Progress")) {
					statusflag = false;
					ridestatus = "Another ride is already in start state!";
				}
			}			
			if(statusflag) {
				DBUtils.startride(rideid,(int)session.getAttribute("userid"));
				ridestatus = "Your ride has started!";
			}
			
			request.setAttribute("ridestatus", ridestatus);
			request.getRequestDispatcher("ridepage").forward(request, response);
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/cancelride")) {
			String status = DBUtils.fetchStatus(Integer.parseInt(request.getParameter("rideid")));			
			String ridestatus="";
			boolean statusflag=true;
			
			//validation							
			if(!(status.equalsIgnoreCase("Created"))) {
					statusflag = false;
					ridestatus = "Cannot cancel ride!";
				}

			if(statusflag) {
				DBUtils.cancelRide(Integer.parseInt(request.getParameter("rideid")));
				ridestatus = "Your ride has been cancelled!";
			}
			
			request.setAttribute("ridestatus", ridestatus);
			request.getRequestDispatcher("ridepage").forward(request, response);			
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/viewridesoffered")) {
			List<RidesDAO> ridelist = DBUtils.fetchRides((int)session.getAttribute("userid"));
			request.setAttribute("ridelist", ridelist);
			request.getRequestDispatcher("userviewridesoffered.jsp").forward(request, response);		
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/viewridestaken")) {
			List<RidesDAO> ridelist = DBUtils.fetchRidesTaken((int)session.getAttribute("userid"));
			request.setAttribute("ridelist", ridelist);
			request.getRequestDispatcher("userviewridestaken.jsp").forward(request, response);		
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/addpoints")) {
			boolean flag = DBUtils.addPoints((int)session.getAttribute("userid"), Integer.parseInt(request.getParameter("points")));
			request.setAttribute("flag", flag);
			request.getRequestDispatcher("useraddpoints.jsp").forward(request, response);		
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/joinride")) {		
			int ridepoints = DBUtils.fetchRidePoints(Integer.parseInt(request.getParameter("rideid")));
			int userpoints = DBUtils.fetchPoints("user", (int)session.getAttribute("userid"));
			if(userpoints < ridepoints)
				request.setAttribute("status", "Ride points not sufficient");
			else {
				DBUtils.joinRide((int)session.getAttribute("userid"), Integer.parseInt(request.getParameter("rideid")));
				request.setAttribute("status", "You have joined a ride!");
			}
			if(request.getParameter("ridetype").equalsIgnoreCase("car"))
				request.getRequestDispatcher("usertakecarride.jsp").forward(request, response);		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
				
		if (request.getRequestURI().equalsIgnoreCase("/MotorPool/setride")) {
			int userid = (int) session.getAttribute("userid");
			String stops = request.getParameter("source") + "," + request.getParameter("stops") + ","
					+ request.getParameter("destination");

			// start and end time
			String starttimestring = request.getParameter("startdate") + " " + request.getParameter("starthour") + ":"
					+ request.getParameter("startminutes");
			String endtimestring = request.getParameter("startdate") + " " + request.getParameter("endhour") + ":"
					+ request.getParameter("endminutes");
			long starttime = DateConverter.formatStringToDateTime(starttimestring);
			long endtime = DateConverter.formatStringToDateTime(endtimestring);
			System.out.println("time start---"+starttime);
			System.out.println("time end---"+endtime);
			// validation
			Map<Long, Long> ridetime = new HashMap<Long, Long>();
			String ridestatus = "";
			ridetime = DBUtils.ridetimes(userid);
			boolean statusflag = true;
			
			if (ridetime != null) {
				for (Map.Entry<Long, Long> m : ridetime.entrySet()) {
					System.out.println(m.getKey() + " " + m.getValue());
					if ((starttime >= m.getKey() && starttime <= m.getValue())
							|| (endtime >= m.getKey() && endtime <= m.getValue())) {
						ridestatus = "Time selected coincide with your another ride";
						statusflag = false;
					}
				}
			}

			if (statusflag) {
				RidesDAO rides = new RidesDAO();
				rides.setUserid(userid);
				rides.setSeats(Integer.parseInt(request.getParameter("seats")));
				rides.setStarttime(starttime);
				rides.setEndtime(endtime);
				rides.setSource(request.getParameter("source"));
				rides.setDestination(request.getParameter("destination"));
				rides.setStops(stops);
				rides.setRidepoints(Integer.parseInt(request.getParameter("ridepoints")));
				rides.setRidetype(request.getParameter("ridetype"));
				rides.setStatus("Created");
				ridestatus = "Ride successfully created";

				DBUtils.createNewRide(rides);

			}
			request.setAttribute("ridestatus", ridestatus);
			request.getRequestDispatcher("setridepage").forward(request, response);
		}
		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/setridepage")) {
			doGet(request, response);
		}
		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/registeruser")) {		
			//int ridepoints = DBUtils.fetchRidePoints(Integer.parseInt(request.getParameter("rideid")));
			//int userpoints = DBUtils.fetchPoints("user", (int)session.getAttribute("userid"));
			
			UsersDAO user = new UsersDAO();
			user.setName(request.getParameter("name"));
			user.setEmail(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			user.setPhone(request.getParameter("phone"));
			System.out.println("inservlet register");
			boolean flag = DBUtils.checkUser(request.getParameter("email"));
			if(!flag) {
				System.out.println("inservlet register in if");
				DBUtils.createNewUser(user);
				request.setAttribute("update", "User successfully created!");
				request.getRequestDispatcher("userregister.jsp").forward(request, response);
			}
			else {
				System.out.println("inservlet register in else"); 
				request.setAttribute("update", "User already exist with that E-mail id!");
				request.getRequestDispatcher("userregister.jsp").forward(request, response);
			}
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/updatevehicletype")) {
			UsersDAO users = new UsersDAO();
			users.setUserid((int)session.getAttribute("userid"));
			if(request.getParameter("ridetype").equalsIgnoreCase("car")) {
				users.setCarno(request.getParameter("carno"));
				users.setCartype(request.getParameter("cartype"));				
				boolean flag = DBUtils.updateUser(users, "car");
				request.setAttribute("flag", flag);
				request.getRequestDispatcher("updatevehicle?ridetype=car").forward(request, response);
			}
		} 
		
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/searchride")) {		
			String source = request.getParameter("source").toLowerCase();
			String destination = request.getParameter("destination").toLowerCase();
			String ridetype = request.getParameter("ridetype");
			List<RidesDAO> ridelist = DBUtils.searchRides(source,destination, ridetype);
			List<RidesDAO> finalridelist = new ArrayList<RidesDAO>();
			
			
			for(RidesDAO ride: ridelist) {
				String[] stops = ride.getStops().split(",");
				List<String> stopList = Arrays.asList(stops);
				System.out.println(source + " TO "+ destination);
				for(String str: stopList)
					System.out.println(str);
				int startindex = stopList.indexOf(source);
				int destinationindex = stopList.indexOf(destination);
				System.out.println("in searchride servlet--- stops-"+stopList.get(0)+"---"+stopList.get(1));
				System.out.println("in searchride servlet--- startindex-"+startindex);
				System.out.println("in searchride servlet--- destindex-"+destinationindex);
				if(startindex < destinationindex)
					finalridelist.add(ride);
			}
			
			request.setAttribute("ridelist", finalridelist);
			
			if(ridetype.equalsIgnoreCase("car"))
				request.getRequestDispatcher("usertakecarride.jsp").forward(request, response);
						
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/changepassword")) {
			
			UsersDAO user = DBUtils.fetchUser((int)session.getAttribute("userid"));
			if(user.getPassword().equals(request.getParameter("currentpassword"))) {
				DBUtils.changeUserPassword((int)session.getAttribute("userid"), request.getParameter("newpassword"));
				request.setAttribute("flag", "Password changed successfully!");
			}
			else {
				request.setAttribute("flag", "Incorrect Password!");
			}
			
		}
		else if (request.getRequestURI().equalsIgnoreCase("/MotorPool/updatevehicle")) {
			doGet(request, response);
		}

	}
	
	

}
