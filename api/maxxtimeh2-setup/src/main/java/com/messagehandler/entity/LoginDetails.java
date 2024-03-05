package com.messagehandler.entity;

import java.util.List;

import com.messagehandler.entity.Permission;

public class LoginDetails {
	private int userId;
	private String userFirstName;
	private String userLastName;
	private String xsrf_token;
	private String exp;
	private long clientIdleTimeout;
	private List<Role> roles;
	private List<Permission> permissions;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getXsrf_Token() {
		return xsrf_token;
	}
	public void setXsrf_Token(String xsrf_token) {
		this.xsrf_token = xsrf_token;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public long getClientIdleTimeout() {
		return clientIdleTimeout;
	}
	public void setClientIdleTimeout(long clientIdleTimeout) {
		this.clientIdleTimeout = clientIdleTimeout;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder.append("userId: ").append(userId).append(", ")
			   .append("userFirstName:").append(userFirstName).append(", ")
			   .append("userLastName:").append(userLastName).append(", ")
			   .append("xsrf_token:").append(xsrf_token).append(", ")
			   .append("exp:").append(exp).append(", ")
			   .append("clientIdleTimeout:").append(clientIdleTimeout).append(", ");
		return builder.toString();
	}
	
	
}
