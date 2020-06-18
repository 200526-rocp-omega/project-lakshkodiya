package com.revature.controller;

import java.util.List;

import com.revature.models.Account;
import com.revature.services.AccountService;
import com.revature.templates.UserAccountTemplate;


public class AccountController {
	private final AccountService accountService = new AccountService();
	
	public int insert(UserAccountTemplate uat) {
		 return accountService.insert(uat);
	}
	
	
	public Account findByAccId(int id) {
		
			return accountService.findByAccId(id);
	}
	
	public List<Account> findAllAccounts(){
				return accountService.findAll();
	
	}
	public List<Account> findByStatus(int statusId) {
		return accountService.findByStatus(statusId);
}
    public List<Account> findByUId(int uId) {
    	return accountService.findByUId(uId);
    }
    
    public List<Integer> findUId(int accId) {
    	return accountService.findUId(accId);
    }
    
    public int update(Account Acc) {
    	return accountService.update(Acc);
    }
    
    public int withdraw(int id, double amount) {
		return accountService.withdraw(id, amount);
	}

    public int deposit(int id, double amount) {
		return accountService.deposit(id, amount);
	}
    
    public boolean checkStatus(int id) {
    	return accountService.checkStatus(id);
    	
    }
    
    public int insertUserAccount(int uId,int accId) {
    	return accountService.insertUserAccount(uId,accId);
    }
   

}
