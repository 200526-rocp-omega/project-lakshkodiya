package com.revature.models;

import java.util.Objects;

public class Role {
	
	private int id;
	private String role;
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(int id, String role) {
		super();
		this.id = id;
		this.role = role;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, role);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		return id == other.id && Objects.equals(role, other.role);
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}

	
	
}
