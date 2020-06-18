package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.templates.UserAccountTemplate;


public interface IAccountDao {
	
	public int insert(UserAccountTemplate uat);// Create
	
	public int insertUserAccount(int uId,int accId) ;
	
	public List<Account> findAll();// Read
	
	public Account findByAccId(int id);
	
	public List<Account> findByUId(int uId);
	
	public List<Account> findByStatus(int statusId);
	
	public int update(Account Acc);// Update
	
	 public List<Integer> findUId(int accId); //find User Id for giver Account Id
	//public int delete();// Delete
	 
	 public int withdraw(int id, double amount); // withdraw from Account
	 
	 public int deposit(int id, double amount);
}
