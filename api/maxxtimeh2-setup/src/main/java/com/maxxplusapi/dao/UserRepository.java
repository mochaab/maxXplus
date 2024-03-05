package com.maxxplusapi.dao;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import com.maxxplusapi.entity.User;

/**
 * 
 * 
 * @author Nasim
 * 
 *
 */


public interface UserRepository extends JpaRepository<User, Integer>{

	 /**
     * Repository class encapsulates the fetching of data from the database
     */
	
	/**
	 * Find Users by username.
     * @param username 
     */
	List<User> findByUsername (String username);
	
	/**
	 * Find Users by password.
     * @param password
     */
	List<User> findByPassword (String password);
}