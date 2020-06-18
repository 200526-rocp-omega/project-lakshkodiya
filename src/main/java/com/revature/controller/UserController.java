package com.revature.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {

	private final UserService userService = new UserService();
	
	public boolean logout(HttpSession session) {
		try {
			userService.logout(session);
		} catch(NotLoggedInException e) {
			return false;
		}
		return true;
	}
	
	public User findUserById(int id) {
		
			return userService.findById(id);
	}
	
	public List<User> findAllUsers(){
			return userService.findAll();
	
	}
	public int update(User u) {
		return userService.update(u);		
	
	}
	
	public int update1(HttpServletRequest req) {
			return userService.update1(req);
	}
	
	public boolean checkStatus(int id) {
		return userService.checkStatus(id);
	}
}