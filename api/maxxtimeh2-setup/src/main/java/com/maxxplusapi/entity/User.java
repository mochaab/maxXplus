package com.maxxplusapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class represents a user
 * @author Nasim
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
	/**
	 * Represents the Id of a User
	 */
	@Id
	@GeneratedValue
	private int id;
	/**
	 * Represents the name of a User
	 */
	@Column(name= "username")
	private String username;
	/**
	 * Represents the password of a user
	 */
	@Column(name= "password")
	private String password;
	
	/**
	 * 
	 * @return the id of the user 
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * @param id user id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * @return the name of the user
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * 
	 * @param username user name to be set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 
	 * @return the password of the user
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 
	 * @param password user password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}


}
