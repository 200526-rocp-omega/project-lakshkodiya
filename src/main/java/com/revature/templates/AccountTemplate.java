package com.revature.templates;

import java.util.Objects;

public class AccountTemplate {
	private int id;
	private double amount;
	public AccountTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AccountTemplate(int id, double amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
	public int getid() {
		return id;
	}
	public void setid(int id) {
		this.id = id;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, amount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AccountTemplate)) {
			return false;
		}
		AccountTemplate other = (AccountTemplate) obj;
		return id == other.id && Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount);
	}
	@Override
	public String toString() {
		return "AccountTemplate [accid=" + id + ", amount=" + amount + "]";
	}
	

}
