package com.Noline.Salon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Login {
@Autowired
	JdbcTemplate jdbc;

@PostMapping("SignUp")
	String Signup(String Email,String Password) {
	jdbc.update("insert into userInfo (Email , Password) values(?,?)", new Object[] {Email,Password});
	return "Your Account has been successfully created";
	}

@PostMapping("SignIn")
	String SignIn(String Email , String Password) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		Statement stmt = con.createStatement();
		String query = "select password from userInfo where email = '"+Email+"'";
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()) {
			String pwd = rs.getString("password");
			if(pwd.equals(Password)) {
				return "you are a valid user";
			} else {
				return "Password is not correct";
			}	} else {
			return "You are not registered";}
	} catch (ClassNotFoundException | SQLException e) {
		System.out.println("error in try block");
		e.printStackTrace();
	}
	return "";
	}

@PostMapping("ForgotPassword")
public String ForgotPassword(String Email) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		Statement stmt = con.createStatement();
		String query = "select email from userInfo where email = '"+Email+"'";
		ResultSet rs = stmt.executeQuery(query);
		if(rs.next()) {
			String RandomNumber = RandomString();
			String query1 = "update userInfo set OTP = '"+RandomNumber+"' where email = '"+Email+"'" ;
			int i = stmt.executeUpdate(query1);
			if(i>0) {
			return "Please use this OTP to change password = " + RandomNumber;}
		} else {
			return "Please check your email ID as you are not registered with us";
		}
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return "";
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

@PostMapping("ChangePassword")
public String ChangePassword(String Email, String NewPassword, String UserOTP) {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Noline", "root", "passward@1");
		Statement stmt = con.createStatement();
		String query = "select OTP from userInfo where email = '"+Email+"'";
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		String DBOTP = rs.getString("OTP");
		if(DBOTP.equals(UserOTP)) {
			String query1 = "update userInfo set password = '"+NewPassword+"' where OTP = + '"+UserOTP+"'";
			int i = stmt.executeUpdate(query1);
			String query2 = "update userInfo set OTP = null where email = '"+Email+"'";
			stmt.executeUpdate(query2);
			if(i>0) {
				return "Your new Password has been successfully updated";
			}
		} else {
			return "Please enter the correct OTP";
		}
	} catch (ClassNotFoundException | SQLException e) {
		System.out.println("Error in catch block");
		e.printStackTrace();
	}

return "";
}

}
