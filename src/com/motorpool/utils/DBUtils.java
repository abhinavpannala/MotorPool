package com.motorpool.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.motorpool.dao.RidesDAO;
import com.motorpool.dao.UsersDAO;
import com.motorpool.db.connection.DBConnectionProvider;
import com.sun.swing.internal.plaf.synth.resources.synth;

public class DBUtils {

	// ADMIN Operations
	// fetch all rides
	public static synchronized List<RidesDAO> fetchAllRides() {
		PreparedStatement statement = null;
		ResultSet results = null;
		List<RidesDAO> ridelist = new ArrayList<RidesDAO>();
		String sql = "select * from rides";

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				RidesDAO ride = new RidesDAO();
				ride.setRideid(results.getInt("rideid"));
				ride.setUserid(results.getInt("userid"));
				ride.setSource(results.getString("source"));
				ride.setDestination(results.getString("destination"));
				ride.setStops(results.getString("stops"));
				ride.setSeats(results.getInt("seats"));
				ride.setRidepoints(results.getInt("ridepoints"));
				ride.setRidetype(results.getString("ridetype"));
				ride.setStarttime(results.getLong("starttime"));
				ride.setEndtime(results.getLong("endtime"));
				ride.setStatus(results.getString("status"));

				ridelist.add(ride);
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridelist;
	}

	// fetch percentage
	public static synchronized float fetchPercentage() {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select percentage from admin";
		float percentage = 0;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			while (results.next()) {
				percentage = results.getFloat("percentage");
			}

		} catch (Exception exe) {
			return 0;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return percentage;
	}

	// update percentage
	public static synchronized boolean updatePercentage(float percentage) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update admin set percentage=" + percentage;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;

	}

	// activate users
	public static synchronized boolean activateUser(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update users set active='active' where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();

			statement.close();
			sql = "select email from users where userid=" + userid;
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				SendMail.sendMail(results.getString("email"), "MotorPool-Approved",
						"Congratulations! Your registration has been accepted."
								+ "Please login using your EmailId and Password.");
			}

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;

	}

	// deactivate users
	public static synchronized boolean deactivateUser(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update users set active='deactive' where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;

	}
	// RIDERS Operations

	// fetch rides taken
	public static synchronized List<RidesDAO> fetchRidesTaken(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		List<RidesDAO> ridelist = new ArrayList<RidesDAO>();
		String sql = "select rideid from riders where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				ridelist.add(DBUtils.fetchRide(results.getInt("rideid")));
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridelist;
	}

	// search rides
	public static synchronized List<RidesDAO> searchRides(String source, String destination, String ridetype) {
		PreparedStatement statement = null;
		ResultSet results = null;
		List<RidesDAO> ridelist = new ArrayList<RidesDAO>();
		String sql = "select *, (select count(*) from riders where rideid = t1.rideid) as seatsgone from rides as t1 where LOWER(stops) like LOWER('%"
				+ source + "%') and LOWER(stops) like LOWER('%" + destination
				+ "%') and status = 'Created' and ridetype='" + ridetype + "'";

		System.out.println(sql);

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			while (results.next()) {
				if ((results.getInt("seats")) != 0) {
					RidesDAO ride = new RidesDAO();
					System.out.println("1. in searchrides dbutils- seats left--" + (results.getInt("seats") - results.getInt("seatsgone")));
					System.out.println("in searchrides dbutils---" + results.getInt("rideid"));
					ride.setRideid(results.getInt("rideid"));
					ride.setUserid(results.getInt("userid"));
					ride.setSource(results.getString("source"));
					ride.setDestination(results.getString("destination"));
					ride.setStops(results.getString("stops"));
					ride.setSeats(results.getInt("seats"));
					ride.setRidepoints(results.getInt("ridepoints"));
					ride.setRidetype(results.getString("ridetype"));
					ride.setStarttime(results.getLong("starttime"));
					ride.setEndtime(results.getLong("endtime"));
					ride.setStatus(results.getString("status"));

					ridelist.add(ride);
				}
			}

		} catch (Exception e) {
			System.out.println("exception in dbutils---" + e.toString());
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return ridelist;
	}

	// join ride
	public static synchronized boolean joinRide(int userid, int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "";
		try {
			Connection conn = DBConnectionProvider.getConnection();
			sql = "insert into riders(rideid,userid) values(?,?)";
			statement = conn.prepareStatement(sql);
			statement.setInt(1, rideid);
			statement.setInt(2, userid);
			System.out.println("in joinride db utils");
			statement.execute();

			statement.close();
			sql = "update rides set seats= seats-1 where rideid=" + rideid;
			statement = conn.prepareStatement(sql);
			statement.execute();

		} catch (Exception exe) {
			System.out.println("exception- joinride" + exe.toString());
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// RIDES Operations
	public static synchronized boolean checkVehicle(String type, int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "";

		if (type.equalsIgnoreCase("car")) {
			sql = "select carno from users where userid=" + userid;
		}

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				if (results.getString(1).equals(null)) {
					System.out.println("in if");
					return false;
				} else {
					System.out.println("in else");
					return true;
				}
			}
		} catch (Exception exe) {
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// create new ride
	public static synchronized boolean createNewRide(RidesDAO rides) {
		PreparedStatement statement = null;
		ResultSet results = null;
		int value = getMaxValue("rideid", "rides");
		String sql = "insert into rides(rideid,userid,ridetype,source,destination,stops,ridepoints,seats,status,starttime,endtime) values(?,?,?,?,?,?,?,?,?,?,?)";

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, value);
			statement.setInt(2, rides.getUserid());
			statement.setString(3, rides.getRidetype());
			statement.setString(4, rides.getSource().toLowerCase());
			statement.setString(5, rides.getDestination().toLowerCase());
			statement.setString(6, rides.getStops().toLowerCase());
			statement.setInt(7, rides.getRidepoints());
			statement.setInt(8, rides.getSeats());
			statement.setString(9, rides.getStatus());
			statement.setLong(10, rides.getStarttime());
			statement.setLong(11, rides.getEndtime());
			return statement.execute();

		} catch (Exception exe) {
			System.out.println("in exception-" + exe.toString());
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

	}

	// fetch rides
	public static synchronized List<RidesDAO> fetchRides(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		List<RidesDAO> ridelist = new ArrayList<RidesDAO>();
		String sql = "select * from rides where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				RidesDAO ride = new RidesDAO();
				ride.setRideid(results.getInt("rideid"));
				ride.setUserid(results.getInt("userid"));
				ride.setSource(results.getString("source"));
				ride.setDestination(results.getString("destination"));
				ride.setStops(results.getString("stops"));
				ride.setSeats(results.getInt("seats"));
				ride.setRidepoints(results.getInt("ridepoints"));
				ride.setRidetype(results.getString("ridetype"));
				ride.setStarttime(results.getLong("starttime"));
				ride.setEndtime(results.getLong("endtime"));
				ride.setStatus(results.getString("status"));

				ridelist.add(ride);
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridelist;
	}

	// fetch ride
	public static synchronized RidesDAO fetchRide(int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		RidesDAO ride = new RidesDAO();
		String sql = "select * from rides where rideid=" + rideid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {

				ride.setRideid(results.getInt("rideid"));
				ride.setUserid(results.getInt("userid"));
				ride.setSource(results.getString("source"));
				ride.setDestination(results.getString("destination"));
				ride.setStops(results.getString("stops"));
				ride.setSeats(results.getInt("seats"));
				ride.setRidepoints(results.getInt("ridepoints"));
				ride.setRidetype(results.getString("ridetype"));
				ride.setStarttime(results.getLong("starttime"));
				ride.setEndtime(results.getLong("endtime"));
				ride.setStatus(results.getString("status"));
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return ride;
	}

	// fetch starttime and endtime
	public static synchronized Map<Long, Long> ridetimes(int userid) {
		Map<Long, Long> ridetime = new HashMap<Long, Long>();
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select starttime,endtime from rides where userid=" + userid;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				ridetime.put(results.getLong("starttime"), results.getLong("endtime"));
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridetime;
	}

	// fetch status of rides
	public static synchronized List<RidesDAO> fetchRideStatuses(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		List<RidesDAO> ridestatuses = new ArrayList<RidesDAO>();
		String sql = "select status,rideid from rides where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				RidesDAO ride = new RidesDAO();
				ride.setRideid(results.getInt("rideid"));
				ride.setStatus(results.getString("status"));
				ridestatuses.add(ride);
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridestatuses;
	}

	// start ride
	public static synchronized boolean startride(int rideid, int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update rides set status = 'Progress' where rideid=" + rideid;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();
			statement.close();

			// fetch ride points
			int ridepoints = 0;
			sql = "select ridepoints from rides where rideid =" + rideid;
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			while (results.next()) {
				ridepoints = results.getInt("ridepoints");
				System.out.println("startride-dbutils-whileloop1---" + results.getInt("ridepoints"));
			}
			statement.close();
			results.close();

			// fetch riders joined
			int users = 0;
			sql = "select * from riders where rideid = " + rideid;
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			// deduct points from rider
			while (results.next()) {
				PreparedStatement statement2 = null;
				sql = "update users set points = points - " + ridepoints + " where userid =" + results.getInt("userid");
				statement2 = conn.prepareStatement(sql);
				statement2.execute();
				statement2.close();

			}

			statement.close();
			sql = "update riders set status = 'Progress' where rideid=" + rideid;
			statement = conn.prepareStatement(sql);
			statement.execute();

		} catch (Exception e) {
			System.out.println("Exception in startride dbutils---" + e.toString());
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// end ride
	public static synchronized boolean endride(int userid, int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update rides set status = 'Completed' where rideid=" + rideid;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();
			statement.close();

			RidesDAO ride = fetchRide(rideid);
			int actualpoints = ride.getRidepoints();
			int points = 0;
			float percentage = fetchPercentage();
			int seats = 0;
			sql = "select count(*) from riders where rideid =" + rideid;
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				seats = results.getInt(1);
			}
			actualpoints = actualpoints * seats;
			points = actualpoints - (int) ((actualpoints * percentage) / 100);
			addPoints(userid, points);
			statement.close();
			sql = "update riders set status = 'Completed' where rideid=" + rideid;
			statement = conn.prepareStatement(sql);
			statement.execute();

			statement.close();

			sql = "update admin set adminpoints= adminpoints + " + (int) ((actualpoints * percentage) / 100);
			statement = conn.prepareStatement(sql);
			statement.execute();

		} catch (Exception e) {
			System.out.println("exception-endride--dbutils---" + e.toString());
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// cancel ride
	public static synchronized boolean cancelRide(int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "";
		try {
			Connection conn = DBConnectionProvider.getConnection();
			sql = "update rides set status = 'Cancelled' where rideid=" + rideid;
			statement = conn.prepareStatement(sql);
			statement.execute();
			statement.close();

			sql = "update riders set status = 'Cancelled' where rideid=" + rideid;
			statement = conn.prepareStatement(sql);
			statement.execute();
		} catch (Exception exe) {
			return false;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// fetchstatus

	public static synchronized String fetchStatus(int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select status from rides where rideid=" + rideid;
		String status = "";
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				status = results.getString("status");
			}

		} catch (Exception exe) {
			return "";
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return status;
	}

	// fetch ride points
	public static synchronized int fetchRidePoints(int rideid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select ridepoints from rides where rideid=" + rideid;
		int ridepoints = 0;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				ridepoints = results.getInt("ridepoints");
			}

		} catch (Exception exe) {
			return 0;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return ridepoints;
	}

	// USERS Operations
	// fetch user with email and password
	public static synchronized boolean checkUser(String email) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select count(*) AS count from users where email=?";
		boolean flag = false;
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setString(1, email);
			results = statement.executeQuery();

			while (results.next()) {
				if (results.getInt("count") != 0) {
					flag = true;
					System.out.println("TRUE");
				} else {
					flag = false;
					System.out.println("FALSE");
				}
			}

		} catch (Exception exe) {
			System.out.println("Exception!" + exe.toString());
			return true;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return flag;
	}

	// add user
	public static synchronized boolean createNewUser(UsersDAO users) {
		PreparedStatement statement = null;
		ResultSet results = null;
		int value = getMaxValue("userid", "users");

		String sql = "insert into users(userid,name,email,password,phone,points,active) values (?,?,?,?,?,?,?)";

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setInt(1, value);
			statement.setString(2, users.getName());
			statement.setString(3, users.getEmail());
			statement.setString(4, users.getPassword());
			statement.setString(5, users.getPhone());
			statement.setInt(6, 20);
			statement.setString(7, "created");
			statement.execute();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// update vehicle details
	public static synchronized boolean updateUser(UsersDAO users, String vehicle) {
		PreparedStatement statement = null;
		ResultSet results = null;

		String sql = "";
		try {
			Connection conn = DBConnectionProvider.getConnection();
			if (vehicle.equalsIgnoreCase("car")) {
				sql = "update users set carno=?,cartype=? where userid=?";
				statement = conn.prepareStatement(sql);
				statement.setString(1, users.getCarno());
				statement.setString(2, users.getCartype());
				statement.setInt(3, users.getUserid());
			}
			statement.execute();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	public static synchronized boolean changeUserPassword(int userid, String password) {
		PreparedStatement statement = null;
		ResultSet results = null;

		String sql = "update users set password=? where userid=?";
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);

			statement.setString(1, password);
			statement.setInt(2, userid);
			statement.execute();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;
	}

	// fetch user details
	public static synchronized UsersDAO fetchUser(int userid) {
		PreparedStatement statement = null;
		ResultSet results = null;
		UsersDAO user = new UsersDAO();
		String sql = "select * from users where userid=" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {

				user.setUserid(userid);
				user.setCarno(results.getString("carno"));
				user.setCartype(results.getString("cartype"));
				user.setEmail(results.getString("email"));
				user.setName(results.getString("name"));
				user.setPassword(results.getString("password"));
				user.setPhone(results.getString("phone"));
				user.setPoints(results.getInt("points"));
				user.setActive(results.getString("active"));
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return user;
	}

	// add points
	public static synchronized boolean addPoints(int userid, int points) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "update users set points=points+" + points + " where userid =" + userid;

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			statement.execute();

			statement.close();

		} catch (Exception exe) {
			return false;
		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return true;

	}

	// fetch points
	public static synchronized int fetchPoints(String role, int userid) {
		int points = 0;
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "";
		if (role.equalsIgnoreCase("admin")) {
			sql = "select adminpoints from admin";
		} else if (role.equalsIgnoreCase("user")) {
			sql = "select points from users where userid=" + userid;
		}
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {
				points = results.getInt(1);
			}

		} catch (Exception exe) {

		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return points;
	}

	// fetch counts for admin home
	public static synchronized int[] fetchAdminHomeCounts() {
		int newUserCount = 0, totalUsers = 0, totalRides = 0;

		PreparedStatement statement = null;
		ResultSet results = null;
		String sql1 = "select count(*) as users from users where active='created'";
		String sql2 = "select count(*) as users from users";
		String sql3 = "select count(*) as rides from rides";

		try {
			Connection conn = DBConnectionProvider.getConnection();

			// fetch new users
			statement = conn.prepareStatement(sql1);
			results = statement.executeQuery();
			while (results.next()) {
				newUserCount = results.getInt("users");
			}
			statement.close();
			results.close();

			// fetch total users
			statement = conn.prepareStatement(sql2);
			results = statement.executeQuery();
			while (results.next()) {
				totalUsers = results.getInt("users");
			}
			statement.close();
			results.close();

			// fetch total rides
			statement = conn.prepareStatement(sql3);
			results = statement.executeQuery();
			while (results.next()) {
				totalRides = results.getInt("rides");
			}
			statement.close();
			results.close();

		} catch (Exception exe) {

		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return new int[] { newUserCount, totalUsers, totalRides };
	}

	// fetch all users' details
	public static synchronized List<UsersDAO> fetchAllUsers(boolean onlyNew) {
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "";
		List<UsersDAO> users = new ArrayList<UsersDAO>();

		if (onlyNew)
			sql = "select * from users where active='created'";
		else
			sql = "select * from users where active<>'created'";

		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();

			while (results.next()) {

				UsersDAO user = new UsersDAO();
				user.setUserid(results.getInt("userid"));
				user.setCarno(results.getString("carno"));
				user.setCartype(results.getString("cartype"));
				user.setEmail(results.getString("email"));
				user.setName(results.getString("name"));
				user.setPassword(results.getString("password"));
				user.setPhone(results.getString("phone"));
				user.setPoints(results.getInt("points"));
				user.setActive(results.getString("active"));

				users.add(user);
			}

		} catch (Exception e) {
			return null;
		} finally {
			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}

		return users;
	}

	// to get the last id from table
	public static synchronized int getMaxValue(String column, String table) {
		int value = 0;
		PreparedStatement statement = null;
		ResultSet results = null;
		String sql = "select max(" + column + ") from " + table;

		// int value = ThreadLocalRandom.current().nextInt(100, 299);
		try {
			Connection conn = DBConnectionProvider.getConnection();
			statement = conn.prepareStatement(sql);
			results = statement.executeQuery();
			if (results.next()) {
				value = results.getInt(1) + 1;
			}

		} catch (Exception exe) {

		} finally {

			DBConnectionProvider.closeStatementAndResultSets(statement, results);
		}
		return value;
	}

}
