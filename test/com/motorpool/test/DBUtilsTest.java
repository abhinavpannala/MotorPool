package com.motorpool.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.motorpool.dao.RidesDAO;
import com.motorpool.dao.UsersDAO;
import com.motorpool.utils.*;

public class DBUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testFetchAllRides() {
		// Verify the Count of all rides.
		List<RidesDAO> allRides = DBUtils.fetchAllRides();
		assertEquals(2, allRides.size());
	}

	@Test
	public void testAdminPercentage() {
		// Verify the Admin's Percentage.
		float percentage = DBUtils.fetchPercentage();
		assertEquals(10, percentage, 0);
	}

	@Test
	public void testUserVehicle() {
		// Verify the User's vehicles.
		boolean isCarPresent = DBUtils.checkVehicle("car", 2);
		assertTrue(isCarPresent);
	}

	@Test
	public void testFetchStatus() {
		// Verify the Ride status.
		String status = DBUtils.fetchStatus(1);
		assertEquals("Completed",status);
	}
	
	@Test
	public void testCheckUser() {
		// Verify User exist.
		boolean isUser = DBUtils.checkUser("kajin@gmail.com");
		assertTrue(isUser);
	}
	
	@Test
	public void testRideTimes() {
		// Verify the Count of ride times.
		Map<Long, Long> ridetime = DBUtils.ridetimes(3);
		assertEquals(2, ridetime.size());
	}
	
	@Test
	public void testGetPoints() {
		// Verify the ride points.
		int points = DBUtils.fetchPoints("user", 1);
		assertEquals(20, points);
		
		points = DBUtils.fetchPoints("admin", 1);
		assertEquals(13, points);
	}
	
	@Test
	public void testfetchAllUsers() {
		// Verify the Count of active and new users.
		List<UsersDAO> users = DBUtils.fetchAllUsers(true);
		assertEquals(1, users.size());
		
		users = DBUtils.fetchAllUsers(false);
		assertEquals(3, users.size());
	}
	
	@Test
	public void testfetchAdminHomeCounts() {
		// Verify the Count of users and rides.
		int[] count = DBUtils.fetchAdminHomeCounts();
		// newUserCount
		assertEquals(1, count[0]);
		
		//ridescount
		assertEquals(2, count[2]);
	}
	

	@Test
	public void testGetUserInfo() {
		// Verify the User data.
		UsersDAO user = DBUtils.fetchUser(2);
		assertEquals("kajin@gmail.com", user.getEmail());
	
	}
	
}