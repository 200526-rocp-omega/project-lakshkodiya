package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.revature.exceptions.IllegalParameterException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDao implements IUserDao {

	/*@Override
	public int insert() {
		
		try(Connection conn=ConnectionUtil.getConnection()) {
			Statement stmt= conn.createStatement();
			String sql="INSERT INTO USERS " +" VALUES(2,'LKodiya','pwd','Lakshdeepika','Jain','jain@g.com',3)";
			stmt.executeUpdate(sql);
					
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
		return 1;
	}*/
	@Override
	public int insert1 (User u) {
		
				// Step 1: Get a Connection using ConnectionUtil
				// The Connection interface represents the physical connection to the database
		
		try(Connection conn=ConnectionUtil.getConnection()) {
			
			// step 2: Define Our SQL Statemnets
			
			String columns= "username, password, first_name, last_name, email, role_id";
			String sql="INSERT INTO USERS (" + columns + ") VALUES (?,?,?,?,?,?)";
			// The ? marks are placeholders for input values
			// 
			//they work for PreparedStatements, and are designed to protect from SQL Injection
			
			// Step 3: Obtain our Statement object
			// PreparedStatements are a sub-interface of Statement that provide extra security to prevent
			// SQL Injection. It accomplishes this by allowing to use ? marks that we can replace
			// with whatever data we want
			
			PreparedStatement stmt= conn.prepareStatement(sql);
			stmt.setString(1,u.getUsername());
			stmt.setString(2,u.getPassword());
			stmt.setString(3,u.getFirstname());
			stmt.setString(4,u.getLastname());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole().getId());
				
			// Step 4: Execute the Statement
			return stmt.executeUpdate();
					
					
					
	}catch(SQLException e) {
		
		// Step 5: Perform any exception handling in an appropriate means
		// This particular example might not be what you want to ultimately use
		
		e.printStackTrace();
		//return 0;
	}
		return 0;
}
	
	@Override
	public List<User> findAll() {
		// In this method, we are planning on returning ALL User objects
		// So we prepare the List<User> at the top
		
		List<User> allUsers=new ArrayList();
		
		// Below is a try-with-resources block
		// IT allows us to instantiate some variable for use only inside the try block
		// And then at the end , it will automatically invoke the close() method on the resource
		// The close() method prevents memory leaks
		
		try(Connection conn=ConnectionUtil.getConnection()) {
			String sql= "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id=ROLES.id ORDER BY USERS.ID ASC";
		
			Statement stmt=conn.createStatement();
			
			// Steps 1 - 3 are the same as listed above, except there is
			// a ResultSet returned from executing our statement
			
			// We must make sure to use this ResultSet to save our data to the List that was prepared at the top
			// The ResultSet interface represents all of the data obtained from our query.
			// It has data for every column that we obtained from our query, per record
		
			
			ResultSet rs=stmt.executeQuery(sql);
			
			// ResultSets are similar to iterators, so this while-loop will allow us to iterate over every record
			
			while(rs.next()) {
				
				// We obtain the data from every column that we need
				
				int id=rs.getInt("id");
				String username=rs.getString("username");
				
				String password=rs.getString("password");
				String firstname= rs.getString("first_name");
				String lastname=rs.getString("last_name");
				String email=rs.getString("email");
				int roleId=rs.getInt("role_id");
				String roleName=rs.getString("role");
				
				// And use that data to create a User object accordingly
				
				Role r=new Role(roleId,roleName);
				User u=new User(id,username,password,firstname,lastname,email,r);
				
				// Then we make sure to add this User to our list
				allUsers.add(u);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList();
		}
		
		return allUsers;
	}
	
	@Override
	public User findByUsername(String username) {


        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
             ResultSet rs = stmt.executeQuery();
              while(rs.next()) {
            	  int id = rs.getInt("id");
            	  String password = rs.getString("password");
            	  String firstName = rs.getString("first_name");
            	  String lastName = rs.getString("last_name");	
            	  String email = rs.getString("email");
            	  int roleId = rs.getInt("role_id");
            	  String roleName = rs.getString("role");
          
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }

        } catch(SQLException e) {

            e.printStackTrace();

        }

        
         return null;
	}

	
	
	@Override
	public User findById(int id) {
		

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
             ResultSet rs = stmt.executeQuery();
              while(rs.next()) {
            	//  int id = rs.getInt("id");
            	  String userName=rs.getString("username");
            	  String password = rs.getString("password");
            	  String firstName = rs.getString("first_name");
            	  String lastName = rs.getString("last_name");	
            	  String email = rs.getString("email");
            	  int roleId = rs.getInt("role_id");
            	  String roleName = rs.getString("role");
          
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id,userName, password, firstName, lastName, email, role);
            }

        } catch(SQLException e) {

            e.printStackTrace();

        }

        throw new IllegalParameterException("Invalid User Id");
         //return null;
	}

	@Override
	public int update(User u) {
		try(Connection conn=ConnectionUtil.getConnection()) {
			//System.out.println("inside update");
			String sql="UPDATE USERS SET ID=? ,USERNAME=? ,PASSWORD=?, FIRST_NAME=?, LAST_NAME=? , EMAIL=?, ROLE_ID=? where ID=?";
			PreparedStatement stmt= conn.prepareStatement(sql);
		   //System.out.println("userid "+u.getId());
			stmt.setInt(1, u.getId());
		    stmt.setString(2, u.getUsername());
		    stmt.setString(3, u.getPassword());
		    stmt.setString(4, u.getFirstname());
		    stmt.setString(5, u.getLastname());
		    stmt.setString(6, u.getEmail());
		    stmt.setInt(7,u.getRole().getId());
		    stmt.setInt(8, u.getId());
		    return stmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();

		}
	return 0;
	}
	
	@Override
	public int update1(HttpServletRequest req) {
		try(Connection conn=ConnectionUtil.getConnection()) {
			String sql="UPDATE USERS SET ID=? ,USERNAME=? ,PASSWORD=?, FIRST_NAME=?, LAST_NAME=? , EMAIL=?, ROLE_ID=? where ID=?";
			PreparedStatement stmt= conn.prepareStatement(sql);
			
			stmt.setInt(1, Integer.parseInt(req.getParameter("id")));
			stmt.setString(2,req.getParameter("username"));
			stmt.setString(3,req.getParameter("password"));
			stmt.setString(4,req.getParameter("fname"));
			stmt.setString(5,req.getParameter("lname"));
			stmt.setString(6, req.getParameter("email"));
			stmt.setInt(7, Integer.parseInt(req.getParameter("role_id")));
			stmt.setInt(8, Integer.parseInt(req.getParameter("id")));
			
			return stmt.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();

			}
		
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
