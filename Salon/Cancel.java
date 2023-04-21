package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Cancel {
	
@GetMapping("cancelBooking")
	String cancelBooking(String BookingID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select BookingID from sp_book_info where BookingId = ?");
			stmt.setString(1, BookingID);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				PreparedStatement rsUpdate= con.prepareStatement("update sp_book_info set service = null, Price = null, UniqueID = null, "
						+ "Email = null , BookingID = null, Status = ? where BookingId = ?");
				rsUpdate.setString(1, "Available");
				rsUpdate.setString(2, BookingID);
				int i = rsUpdate.executeUpdate();
				if(i>0) {
					return "Your booking has been cancelled successfully";
				}
			}
			else {
				return "Please enter the correct bookingID";
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
}

}
