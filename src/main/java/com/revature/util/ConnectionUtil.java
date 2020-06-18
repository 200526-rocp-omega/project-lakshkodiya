package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//jdbc:oracle:thin:@ENDPOINT:1521:ORCL


public class ConnectionUtil {
	
	private static Connection conn=null;
	
	private ConnectionUtil() {
		super();
		
	}

	public static Connection getConnection() {
		
		/* We will be using Driver Manager to obtain our*/
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 
			try {
				conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@training.cyk1tgtvjfwa.us-east-1.rds.amazonaws.com:1521:ORCL",
						"beaver","chew");
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}catch(ClassNotFoundException e) {
			System.out.println("Did not find Oracle JDBC Driver class");
		}
	
	return conn;
	}
	
}