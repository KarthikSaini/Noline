package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Book {
@Autowired 
JdbcTemplate jdbc;

@GetMapping("BookServices")
	String BookServices(int UniqueID, int UniqueID_SP, String Service) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		PreparedStatement stmt = con.prepareStatement("select Email from userInfo where UniqueID = ?");
		stmt.setInt(1, UniqueID);
		ResultSet rs = stmt.executeQuery();
		String BookID = RandomString();
		if(rs.next()) {
			String EmailID = rs.getString("Email");
			PreparedStatement stmtForSer = con.prepareStatement("select * from services where UniqueID_SP = ?");
			stmtForSer.setInt(1, UniqueID_SP);
			ResultSet rsForSer = stmtForSer.executeQuery();
			while(rsForSer.next()) {
				String Ser = rsForSer.getString("Service");
				if(Ser.equals(Service)) {
					int Pr = rsForSer.getInt("Price");
					PreparedStatement stmtForBookSer = con.prepareStatement("insert into book_info_user values (?,?,?,?,?,?)");
					stmtForBookSer.setInt(1, UniqueID);
					stmtForBookSer.setString(2, EmailID);
					stmtForBookSer.setString(3, BookID);
					stmtForBookSer.setString(4, Service);
					stmtForBookSer.setInt(5, Pr);
					stmtForBookSer.setInt(6, UniqueID_SP);
					
					int i = stmtForBookSer.executeUpdate();
					if(i>0) {
						return "your service has been successfuly booked, please use the "
								+ "mentioned Booking ID to Book time slot ;   BookingID = "+BookID;
					}
				}
			}
			if(rsForSer.next()) {
				return "";
			} else {
				return "Please chose the correct service";
			} 
			
		} else {
			return "Please insert the valid UniqueID";
		}
	
	
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
	return null;
	}

public String RandomString(){
	int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 6;
    Random random = new Random();

    String generatedString = random.ints(leftLimit, rightLimit + 1)
      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
      .limit(targetStringLength)
      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
      .toString();

    return generatedString;
    }	    

@GetMapping("BookTimeSlot")
	String BookTimeSlot(String StartTime, String BookingID, String Date) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		PreparedStatement stmt = con.prepareStatement("select * from book_info_user where BookingID = ?");
		stmt.setString(1, BookingID);
		ResultSet rs = stmt.executeQuery();
		if(rs.next()) {
			int RecUniqueId_SP = rs.getInt("UniqueId_SP");
			int RecPrice = rs.getInt("Price");
			int RecUniqueId = rs.getInt("UniqueId");
			String RecEmail = rs.getString("Email");
			String RecService = rs.getString("Service");
			PreparedStatement stmtForCheAva = con.prepareStatement("select Status from sp_book_info where StartTime = ? and UniqueID_SP = ?"
					+ " and Date = ? ");
			stmtForCheAva.setString(1, StartTime);
			stmtForCheAva.setInt(2, RecUniqueId_SP);
			stmtForCheAva.setString(3, Date);
			ResultSet rsForCheAva = stmtForCheAva.executeQuery();
			if(rsForCheAva.next()) {
				String status = rsForCheAva.getString("Status");
				if(status.equals("Available")) {
					PreparedStatement stmtForIns = con.prepareStatement("update sp_book_info set service = ?, Price = ? "
							+ ", BookingID = ? , UniqueId = ? , Email = ?, Status = ? where StartTime = ? and UniqueID_SP = ?");
					stmtForIns.setString(1, RecService);
					stmtForIns.setInt(2, RecPrice);
					stmtForIns.setString(3, BookingID);
					stmtForIns.setInt(4, RecUniqueId);
					stmtForIns.setString(5, RecEmail);
					stmtForIns.setString(6, "Booked");
					stmtForIns.setString(7, StartTime);
					stmtForIns.setInt(8, RecUniqueId_SP);
					int i = stmtForIns.executeUpdate();
					if(i>0) {
						return "your Time Slot has been successfuly booked";
					}
				} else {return "Slot is already booked or Unavailable";}
			}
			else {return "No time slot is Available";}
			
		} else {
			return "Please enter the correct bookingID or please book the service first";
		}
	
	
	} catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
	}
	return null;
	}

	}


