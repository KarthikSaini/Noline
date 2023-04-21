package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Salon_Parlour {

@Autowired 
	JdbcTemplate jdbc;

@GetMapping("Search/Salon_Parlour")
public Map SalonParlourName() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		PreparedStatement stmt = con.prepareStatement("select * from salon_parlour_info");
		ResultSet rs = stmt.executeQuery();
		ArrayList list = new ArrayList();
		while(rs.next()) {
			Map map = new HashMap();
			map.put("Salon_Parlour_Name", rs.getString("Salon_Parlour_Name"));
			map.put("Address", rs.getString("Address"));
			map.put("Timing", rs.getString("Timing"));
			map.put("Rating", rs.getInt("Rating"));
			map.put("UniqueID_SP", rs.getInt("UniqueID_SP"));
			list.add(map);
		}
		Map newMap = new HashMap();
		newMap.put("All Salon_Parlour_Name", list);
		return newMap;
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
	return null;
	}


@GetMapping("Search/Salon_Parlour/Filter/{UniqueId}/{Rating}/{OpeningTime}/{State}/{District}")
	public Map SalonParlourNam(@PathVariable("UniqueId") int UniqueId,@PathVariable("Rating") int Rating, 
			@PathVariable("OpeningTime") String OpeningTime ,@PathVariable("State") String State,
		@PathVariable("District") String District) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");	
		PreparedStatement stmt = con.prepareStatement("select * from salon_parlour_info where UniqueID_SP = ? or Rating = ? or OpeningTime = ? "
				+ "or State = ? or District = ?");
		stmt.setInt(1, UniqueId);
		stmt.setInt(2, Rating);
		stmt.setString(3, OpeningTime);
		stmt.setString(4, State);
		stmt.setString(5, District);
		ResultSet rs = stmt.executeQuery();
		ArrayList list = new ArrayList();
		while(rs.next()) {
			Map map = new HashMap();
			map.put("Salon_Parlour_Name", rs.getString("Salon_Parlour_Name"));
			map.put("Address", rs.getString("Address"));
			map.put("Timing", rs.getString("Timing"));
			map.put("Rating", rs.getInt("Rating"));
			map.put("UniqueID_SP", rs.getInt("UniqueID_SP"));
			list.add(map);
		}
		Map newMap = new HashMap();
		newMap.put("All Salon_Parlour_Name", list);
		return newMap;
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
	return null;
	}

}
