package com.maxxplusapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxplusapi.entity.User;
import com.maxxplusapi.service.UserService;

/**
 * Controller class for Users
 * 
 * 
 *
 */
@CrossOrigin
@RestController
public class UserController {
	
	 /**
     * This class is deprecated. Originally our API was a microservice so this class was used.
     * However, we everntually changed the structure to an integrated one so instead of accessing the endpoints offered by this class, we directly accessed the service classes.
     */

	@Autowired
	private UserService userService;
	
	@PostMapping("/addUser")
	public User addUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	@PostMapping("/addUsers")
	public List<User> addUsers(@RequestBody List<User> users) {
		return userService.createUsers(users);
	}
	
	@GetMapping("/user/{id}")
	public User getUserById(@PathVariable int id) {
		return userService.getUserByID(id);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getUsers();
	}
	
	@PutMapping("/updateUser")
	public User updateUser(@RequestBody User user) {
	    return userService.updateUser(user);
	}
	
	@DeleteMapping("/user/{id}")
	public String deleteUser(@PathVariable int id) {
	    return userService.deleteUserById(id);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<List<User>> getUserByUsername(@RequestParam String username){
	    return new ResponseEntity<List<User>>(userService.getUserByUsername(username), HttpStatus.OK);
	}
	
	@GetMapping("/user/{password}")
	public ResponseEntity<List<User>> getUserByPassword (@RequestParam String password){
	    return new ResponseEntity<List<User>>(userService.getUserByPassword(password), HttpStatus.OK);
	}
}