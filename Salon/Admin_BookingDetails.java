package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Admin_BookingDetails {
	
	@GetMapping("Search/All_Booking_Details")
	public Map All_Booking_Details() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select * from sp_book_info where Status = ?");
			stmt.setString(1, "Booked");
			ResultSet rs = stmt.executeQuery();
			ArrayList list = new ArrayList();
			while(rs.next()) {
				Map map = new HashMap();
				map.put("Date", rs.getString("Date"));
				map.put("Status", rs.getString("Status"));
				map.put("Service", rs.getString("Service"));
				map.put("Price", rs.getInt("Price"));
				map.put("BookingID", rs.getString("BookingID"));
				map.put("StartTime", rs.getString("StartTime"));
				map.put("EndTime", rs.getString("EndTime"));
				map.put("UniqueIDCustomer", rs.getInt("UniqueID"));
				map.put("Email", rs.getString("Email"));
				map.put("Salon_Parlour_Name", rs.getString("Salon_parlour_name"));
				list.add(map);
			}
			Map newMap = new HashMap();
			newMap.put("All booking", list);
			return newMap;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		}
	
	@GetMapping("Search/All_Status_Details/{Status}")
	public Map All_Status_Details(@PathVariable("Status") String Status) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select * from sp_book_info where Status = ?");
			stmt.setString(1, Status);
			ResultSet rs = stmt.executeQuery();
			ArrayList list = new ArrayList();
			int count = 0;
			while(rs.next()) {
				Map map = new HashMap();
				map.put("Date", rs.getString("Date"));
				map.put("Status", rs.getString("Status"));
				map.put("StartTime", rs.getString("StartTime"));
				map.put("EndTime", rs.getString("EndTime"));
				map.put("Salon_Parlour_Name", rs.getString("Salon_parlour_name"));
				list.add(map);
				count++;
			}
			Map newMap = new HashMap();
			newMap.put("All booking", list);
			newMap.put("The total "+Status+" slots in database are", count);
			return newMap;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		}


}
