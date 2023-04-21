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
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ClientBookingDetails {

	@GetMapping("All_Customers_Booking_Details")
	public Map All_Customers_Booking_Details(int OuniqueID_SP) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select * from sp_book_info where UniqueID_SP = ? and Status = ?");
			stmt.setInt(1, OuniqueID_SP);
			stmt.setString(2, "Booked");
			ResultSet rs = stmt.executeQuery();
			ArrayList list = new ArrayList();
			String salonName = "";
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
				list.add(map);
				salonName = rs.getString("Salon_parlour_name");
			}
			list.add(salonName);
			list.add(OuniqueID_SP);
			Map newMap = new HashMap();
			newMap.put("All upcoming booking", list);
			return newMap;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		}
	
@GetMapping("Slot_Details")
	public Map Slot_Details(int UniqueID_SP, String Date) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select * from sp_book_info where UniqueID_SP = ? and Date = ?");
			stmt.setInt(1, UniqueID_SP);
			stmt.setString(2, Date);
			ResultSet rs = stmt.executeQuery();
			ArrayList list = new ArrayList();
			String salonName = "";
			while(rs.next()) {
				Map map = new HashMap();
				map.put("Date", rs.getString("Date"));
				map.put("Status", rs.getString("Status"));
				map.put("StartTime", rs.getString("StartTime"));
				map.put("EndTime", rs.getString("EndTime"));
				list.add(map);
				salonName = rs.getString("Salon_parlour_name");
			}
			list.add(salonName);
			list.add(UniqueID_SP);
			Map newMap = new HashMap();
			newMap.put("Status of All Slots on "+ Date , list);
			return newMap;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
		}

}
