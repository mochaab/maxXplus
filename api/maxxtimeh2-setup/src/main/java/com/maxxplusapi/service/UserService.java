package com.maxxplusapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxplusapi.dao.UserRepository;
import com.maxxplusapi.entity.User;


/**
 * This class is a service class for user, it contains the business logic
 * 
 * @author Nasim
 * 
 *
 */

@Service
public class UserService {
	
	/**
	 * This class contains the business logic related with User operations
	 * */
	
	/**
	 * Dependency injection of User Repository
	 * */
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Creating user 
	 * @param user User object
	 * */
	
	public User createUser(User user) {
		return userRepository.save(user);
	}
	
	/**
	 * Creating multiple of user 
	 * @param user User: List<User>
	 * */
	public List<User> createUsers(List<User> users){
		return userRepository.saveAll(users);
	}

	/**
	 * Get User by ID 
	 * @param id int
	 * */
	public User getUserByID(int id) {
		return userRepository.findById(id).orElse(null);
	}
	
	/**
	 * Get Users 
	 * */
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	/**
	 * Get Users by username
	 * @param username String
	 * */
	public List<User> getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	/**
	 * Get Users by password
	 * @param password String
	 * */
	public List<User> getUserByPassword(String password) {
		return userRepository.findByPassword(password);
	}
	
	/**
	 * Update user
	 * @param user User
	 * */
	public User updateUser(User user) {
		User oldUser=null;
		Optional<User> optionaluser=userRepository.findById(user.getId());
		if(optionaluser.isPresent()) {
			oldUser=optionaluser.get();
			oldUser.setUsername(user.getUsername());
			oldUser.setPassword(user.getPassword());
			userRepository.save(oldUser);
		}else {
			return new User();
		}
		return oldUser;
	}
	
	/**
	 * Delete user_id
	 * @param user by id
	 * */
	public String deleteUserById(int id) {
		userRepository.deleteById(id);
		return "User got deleted";
	}
}