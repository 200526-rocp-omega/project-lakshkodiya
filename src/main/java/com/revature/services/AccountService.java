package com.revature.services;

import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.IAccountDao;
import com.revature.models.Account;
import com.revature.templates.UserAccountTemplate;


public class AccountService {
	
private IAccountDao dao=new AccountDao();
	
	public int insert(UserAccountTemplate uat) {
		return dao.insert(uat);
	}
	
	public int insertUserAccount(int uId,int accId) {
		return dao.insertUserAccount(uId, accId);
	}
	
	public List<Account> findAll(){
		return dao.findAll();
		
	}
	
	public Account findByAccId(int id) {
		return dao.findByAccId(id);
	}
	public List<Account> findByStatus(int statusId) {
			return dao.findByStatus(statusId);
	}
	
	public List<Account> findByUId(int uId) {
		return dao.findByUId(uId);
	}
	
	
	public int update(Account Acc) {
		//Account ac = dao.findByAccId(Acc.getAccountId());
		//if (ac.getStatus().getStatusId()==2)
		return dao.update(Acc);		
	}
	
	public List<Integer> findUId(int accId) {
		return dao.findUId(accId);
	}
	
	public int withdraw(int id, double amount) {
		return dao.withdraw(id, amount);
	}
	
	public int deposit(int id, double amount) {
		return dao.deposit(id, amount);
	}
	
	public boolean checkStatus(int id) {
		Account acc=dao.findByAccId(id);
		if(acc.getStatus().getStatusId()==2)
			return true;
		else 
			return false;
			
		
	}
	/*public int delete(int id) {
		return dao.delete(id);
	}*/


}
