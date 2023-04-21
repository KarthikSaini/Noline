package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ClientChangeStatus {

@GetMapping("ChangeStatus")
	String changeStatus(int OuniqueID_SP , String Date, String StartTime) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
			PreparedStatement stmt = con.prepareStatement("select * from sp_book_info where UniqueID_SP = ? and Date = ? and StartTime = ?");
			stmt.setInt(1, OuniqueID_SP);
			stmt.setString(2, Date);
			stmt.setString(3, StartTime);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
			int recUniqueID_SP = rs.getInt("UniqueId_SP");
			String recDate = rs.getString("Date");
			String recStartTime = rs.getString("StartTime");
			String recStatus = rs.getString("Status");
			if(recUniqueID_SP == OuniqueID_SP) {
				if(recDate.equals(Date)) {
					if(recStartTime.equals(StartTime)) {
						if(recStatus.equals("Available")) {
							PreparedStatement stmtForUpdate = con.prepareStatement("update sp_book_info set Status = ? where StartTime = ? and "
									+ "Date = ? and UniqueID_SP=? ");
							stmtForUpdate.setString(1, "Unavailable");
							stmtForUpdate.setString(2, recStartTime);
							stmtForUpdate.setString(3, recDate);
							stmtForUpdate.setInt(4, recUniqueID_SP);
							int i = stmtForUpdate.executeUpdate();
							if(i>0) {
								return "Your time slot has been successfully updated to Unavailable";
							} else { return "error in code1";}
						} else if(recStatus.equals("Unavailable")) {
							PreparedStatement stmtForUpdate = con.prepareStatement("update sp_book_info set Status = ? where StartTime = ? and "
									+ "Date = ? and UniqueID_SP=? ");
							stmtForUpdate.setString(1, "Available");
							stmtForUpdate.setString(2, recStartTime);
							stmtForUpdate.setString(3, recDate);
							stmtForUpdate.setInt(4, recUniqueID_SP);
							int i = stmtForUpdate.executeUpdate();
							if(i>0) {
								return "Your time slot has been successfully updated to Available";
							} else { return "error in code2";}
						} else {return "you can not change the status as client has booked the time slot";}
							
					} else { return "Please enter the correct time slot";}
						
				} else { return "Please enter the correct date";}
			} else {
				return "Please enter your correct Unique ID for Salon or Parlour";
			}
			} else {return "Please check your details";}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
}

}
