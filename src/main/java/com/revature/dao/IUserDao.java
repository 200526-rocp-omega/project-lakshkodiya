package com.revature.dao;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.revature.models.User;

public interface IUserDao {
	//public int insert();
	public int insert1(User u);// Create
	
	public List <User> findAll();// Read
	
	public User findById(int id);
	public User findByUsername(String username);
	public int update(User u);
	public int update1(HttpServletRequest req);// Update
	
	public int delete(int id);// Delete
}
