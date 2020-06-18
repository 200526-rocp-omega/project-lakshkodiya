package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDao;
import com.revature.dao.UserDao;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.LoginTemplate;

// The service layer is a layer that is designed to enforce your buisness logic
// These are miscellaneous rules that define how your application will function
// ex: May not withdraw money over the current balance
// 
// All interaction with the DAO will be through this services layer
// This design is simply furthering the same design structure that we have used up to now
// How you go about designing the details of this layer is up to you
// Due to the nature of the " business logic" being rather arbitrary
//
// This layer has the MOST creativity involved
// Most other layers are pretty boilerplate, where you pretty much copy/paste most methods

public class UserService {
	
	
  // A starting place that I like to use , is to also create CRD methods in the service layer
	// that will be used to interact with the DAO
	
	// Then additionally , you can have extra methods to enforce whatever feature/rules that you want
	// For example, we might also have a login/logout method
	
	//public int insert();
	private IUserDao dao=new UserDao();
	
	public int insert1(User u) {
		return dao.insert1(u);
	}
	
	public List <User> findAll(){
		return dao.findAll();
		
	}
	
	public User findById(int id) {
		return dao.findById(id);
	}
	public User findByUsername(String username) {
			return dao.findByUsername(username);
	}
	public int update(User u) {
		return dao.update(u);		
	
	}
		
	public int update1(HttpServletRequest req) {
		return dao.update1(req);		
	}
	
	public int delete(int id) {
		return dao.delete(id);
	}
	
	public boolean checkStatus(int id) {
		boolean stasus;
		User u=dao.findById(id);
		if (u==null)
				return false;
		else return true;
		
	}
	
	
	public User login(LoginTemplate it) {
		//System.out.println(it);
		User userFromDB= findByUsername(it.getUsername());
		//User userFromDB1=findById(it.getId());
		
		if(userFromDB==null) {
			return null;
			
		}
				
				if(userFromDB.getPassword().equals(it.getPassword())) {
					//System.out.println("inside UserService "+userFromDB);
					
					return userFromDB;
				}
		return null;
	}
	
	public void logout(HttpSession session) {
		if(session==null || session.getAttribute("currentUser")==null) {
			throw new NotLoggedInException("User must be logged in, in order to logout.");
		}
			session.invalidate();
			
		
	}
}
