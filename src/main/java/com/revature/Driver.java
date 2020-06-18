package com.revature;

import com.revature.dao.IUserDao;
import com.revature.dao.UserDao;
import com.revature.models.Role;
import com.revature.models.User;

public class Driver {

	public static void main(String[] args) {
		IUserDao dao = new UserDao();
		//new UserDao().insert();
		//User user=new User(8,"username4","password3","first3","last3","email4@yahoo.com",new Role(3,"Premium"));
	//	dao.insert1(user);
		for(User u: dao.findAll())
		{
			System.out.println(u);
		}
		//ConnectionUtil.getConnection();
	}

}
