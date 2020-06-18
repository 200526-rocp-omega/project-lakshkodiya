package com.revature.templates;

import java.util.Objects;

public class UserAccountTemplate {
	private int userId;
	private int accId;
	private double balance;
	private int status_id;
	private int type_id;
	
	
	public UserAccountTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}


	public UserAccountTemplate(int userId, int accId, double balance, int status_id, int type_id) {
		super();
		this.userId = userId;
		this.accId = accId;
		this.balance = balance;
		this.status_id = status_id;
		this.type_id = type_id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getAccId() {
		return accId;
	}


	public void setAccId(int accId) {
		this.accId = accId;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public int getStatus_id() {
		return status_id;
	}


	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}


	public int getType_id() {
		return type_id;
	}


	public void setType_id(int type_id) {
		this.type_id = type_id;
	}


	@Override
	public int hashCode() {
		return Objects.hash(accId, balance, status_id, type_id, userId);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof UserAccountTemplate)) {
			return false;
		}
		UserAccountTemplate other = (UserAccountTemplate) obj;
		return accId == other.accId && Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance)
				&& status_id == other.status_id && type_id == other.type_id && userId == other.userId;
	}


	@Override
	public String toString() {
		return "UserAccountTemplate [userId=" + userId + ", accId=" + accId + ", balance=" + balance + ", status_id="
				+ status_id + ", type_id=" + type_id + "]";
	}
	
	
	
}
