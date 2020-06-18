package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.IllegalParameterException;
import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.templates.UserAccountTemplate;
import com.revature.util.ConnectionUtil;

public class AccountDao implements IAccountDao 

{
	@Override
	public int insert(UserAccountTemplate uat) {
		try(Connection conn=ConnectionUtil.getConnection()) {
			
			String columns= "BALANCE , STATUS_ID , TYPE_ID";
			String sql="INSERT INTO ACCOUNTS (" + columns + ") VALUES (?,?,?)";
			
			PreparedStatement stmt= conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setDouble(1,uat.getBalance());
			stmt.setInt(2,uat.getStatus_id());
			stmt.setInt(3,uat.getType_id());
						
			int st= stmt.executeUpdate();
			if (st!=0) {
				ResultSet rs=stmt.getGeneratedKeys();
				System.out.println("result set "+rs);
				int accId=0;
				if (rs.next()) {
				   accId=rs.getInt(1);
				 }
				return accId;
			}
			
		}catch(SQLException e) {
	
		
			e.printStackTrace();
			}
		
	return 0;
	}
	
	@Override 
	public int insertUserAccount(int uId,int accId) {
		try(Connection conn=ConnectionUtil.getConnection()) {
					
					String columns= "USER_ID, ACCOUNT_ID ";
					String sql="INSERT INTO USERS_ACCOUNTS (" + columns + ") VALUES (?,?)";
					
					PreparedStatement stmt= conn.prepareStatement(sql);
					stmt.setDouble(1,uId);
					stmt.setInt(2,accId);
												
					return stmt.executeUpdate();
				}catch(SQLException e) {
			
				
					e.printStackTrace();
					}
				
			return 0;	
	}
	
	@Override
	public List<Account> findAll() {
		// In this method, we are planning on returning ALL Account objects
		
		List<Account> allAccount=new ArrayList();
		
				
		try(Connection conn=ConnectionUtil.getConnection()) {
			String sql= "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.STATUS_ID =ACCOUNT_STATUS.ID "+
					"INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.TYPE_ID= ACCOUNT_TYPE.ID";
		
			Statement stmt=conn.createStatement();
			
						
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				int id=rs.getInt("ID");
				double balance=rs.getDouble("Balance");
				int status_id=rs.getInt("status_id");
				int type_id=rs.getInt("type_id");
				String status=rs.getString("status");
				String type=rs.getString("type");
				
				AccountStatus AccStatus= new AccountStatus(status_id,status);
				AccountType AccType= new AccountType(type_id,type);
				Account Acc= new Account(id,balance,AccStatus,AccType);
					
				allAccount.add(Acc);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList();
		}
		
		return allAccount;
	}
	
		
	@Override
	public Account findByAccId(int id) {

        try (Connection conn = ConnectionUtil.getConnection()) {

            String sql =  "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.STATUS_ID =ACCOUNT_STATUS.ID "+
					"INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.TYPE_ID= ACCOUNT_TYPE.ID WHERE ACCOUNTS.ID=?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
             ResultSet rs = stmt.executeQuery();
              while(rs.next()) {
            		double balance=rs.getDouble("Balance");
    				int status_id=rs.getInt("status_id");
    				int type_id=rs.getInt("type_id");
    				String status=rs.getString("status");
    				String type=rs.getString("type");
    				
    				AccountStatus AccStatus= new AccountStatus(status_id,status);
    				AccountType AccType= new AccountType(type_id,type);
    				return new Account(id,balance,AccStatus,AccType);
    						
    			
            	  }

        } catch(SQLException e) {

            e.printStackTrace();

        }   
        System.out.println("sql execute");
		 
			throw new IllegalParameterException("No such Account Exist");
		
	
        //return null;
	}

	//public Account findByAccId(int id);
	@Override
	public List<Account> findByUId(int uId) {
		List<Account> allAccount=new ArrayList();
		
		 try (Connection conn = ConnectionUtil.getConnection()) {

	            String sql =  "SELECT * FROM ACCOUNTS INNER JOIN USERS_ACCOUNTS ON ACCOUNTS.ID = USERS_ACCOUNTS.ACCOUNT_ID "+
	            "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.STATUS_ID = ACCOUNT_STATUS.ID "+
						"INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.TYPE_ID= ACCOUNT_TYPE.ID WHERE  USERS_ACCOUNTS.USER_ID=?";

	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, uId);
	             ResultSet rs = stmt.executeQuery();
	              while(rs.next()) {
	            	    int id=rs.getInt("Id");
	            		double balance=rs.getDouble("Balance");
	    				int status_id=rs.getInt("status_id");
	    				int type_id=rs.getInt("type_id");
	    				String status=rs.getString("status");
	    				String type=rs.getString("type");
	    				
	    				AccountStatus AccStatus= new AccountStatus(status_id,status);
	    				AccountType AccType= new AccountType(type_id,type);
	    				
	    				Account Acc=new Account(id,balance,AccStatus,AccType);
	    				allAccount.add(Acc);
	    				
	              }
	              
		 	}  catch(SQLException e) {

	              e.printStackTrace();

	           }   

	    if (allAccount.size()!=0)	
	    	 return allAccount;
	    else
	    	throw new IllegalParameterException("No such user Id exist or no account found under this Id");
	    
	
	}
	
	@Override
	public List<Account> findByStatus(int statusId) {
		List<Account> allAccount=new ArrayList();
		if (statusId < 1 || statusId > 4 )
			throw new IllegalParameterException("Invalid Status");
		  try (Connection conn = ConnectionUtil.getConnection()) {

	            String sql =  "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.STATUS_ID = ACCOUNT_STATUS.ID "+
						"INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.TYPE_ID = ACCOUNT_TYPE.ID WHERE ACCOUNTS.STATUS_ID = ?";

	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, statusId);
	             ResultSet rs = stmt.executeQuery();
	              while(rs.next()) {
	            	    int id=rs.getInt("Id");
	            		double balance=rs.getDouble("Balance");
	    				//int status_id=rs.getInt("status_id");
	    				int type_id=rs.getInt("type_id");
	    				String status=rs.getString("status");
	    				String type=rs.getString("type");
	    				
	    				AccountStatus AccStatus= new AccountStatus(statusId,status);
	    				AccountType AccType= new AccountType(type_id,type);
	    				
	    				Account Acc= new Account(id,balance,AccStatus,AccType);
	    				allAccount.add(Acc);
	    			
	            	  }

	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
   	        return allAccount;

	}	
	@Override
	public List<Integer> findUId(int accId) {
		List<Integer> uIds = new ArrayList();
		 try (Connection conn = ConnectionUtil.getConnection()) {

		String sql = "SELECT USER_ID FROM  USERS_ACCOUNTS WHERE USERS_ACCOUNTS.ACCOUNT_ID=?";

	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setInt(1,accId);
	             ResultSet rs = stmt.executeQuery();
	             while(rs.next()) {
	            	 uIds.add(rs.getInt("USER_ID"));
	             }
	              //uId= rs.getInt("USER_ID");
		 }catch(SQLException e) {
	            e.printStackTrace();
	        }

		return uIds;
	}
	
	
	@Override
	public int update(Account Acc) {
		 try (Connection conn = ConnectionUtil.getConnection()) {
			 
				String sql = "UPDATE ACCOUNTS SET BALANCE=?, STATUS_ID=?, TYPE_ID=?  WHERE ACCOUNTS.ID=?";
			            PreparedStatement stmt = conn.prepareStatement(sql);
			            stmt.setDouble(1,Acc.getBalance());
			            stmt.setInt(2, Acc.getStatus().getStatusId());
			            stmt.setInt(3, Acc.getType().getTypeId());
			            stmt.setInt(4, Acc.getAccountId());
			            return stmt.executeUpdate();
		 }catch(SQLException e) {
	            e.printStackTrace();
	        }

		 return 0;
	}	

	public int withdraw(int id, double amount) {
		 try (Connection conn = ConnectionUtil.getConnection()) {
			 
						String sql = "UPDATE ACCOUNTS SET BALANCE=BALANCE-? WHERE ACCOUNTS.ID=? AND ACCOUNTS.STATUS_ID=?";
								//AND ACCOUNTS.STATUS_ID=?";
			            PreparedStatement stmt = conn.prepareStatement(sql);
			            stmt.setDouble(1,amount);
			            stmt.setInt(2,id);
			            stmt.setInt(3, 2);
			            //System.out.println("before  sql withdraw");
			            return stmt.executeUpdate();
			           // System.out.println("after sql withdraw");
		 	}catch(SQLException e) {
	            e.printStackTrace();
	        }


		return 0;
	}
	
	public int deposit(int id, double amount) {
		 try (Connection conn = ConnectionUtil.getConnection()) {
			 
						String sql = "UPDATE ACCOUNTS SET BALANCE=BALANCE+? WHERE ACCOUNTS.ID=? AND ACCOUNTS.STATUS_ID=?";
								//AND ACCOUNTS.STATUS_ID=?";
			            PreparedStatement stmt = conn.prepareStatement(sql);
			            stmt.setDouble(1,amount);
			            stmt.setInt(2,id);
			            stmt.setInt(3, 2);
			            //System.out.println("before  sql deposit");
			            return stmt.executeUpdate();
			           
		 	}catch(SQLException e) {
	            e.printStackTrace();
	        }


		return 0;
	}

	
}
