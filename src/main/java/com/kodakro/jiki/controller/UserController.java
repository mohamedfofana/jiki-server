package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.model.User;
import com.kodakro.jiki.service.UserService;

@RestController
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		User dbUser = userService.register(user);
		if (dbUser == null) {
			return new ResponseEntity<CustomResponseType>(new CustomResponseType("User with "+ user.getUsername() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(dbUser, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public List<User> getUsers(){
		return userService.getUsers();
	}

	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable("id") Long id){
		return userService.getUserById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTeamById(@PathVariable("id") Long id){
		userService.deleteUserById(id);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> patchUser(@PathVariable("id") Long id, @Valid @RequestBody User user) {
		userService.patchUser(id, user);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateTeam(@PathVariable(value = "id") Long id, @Valid @RequestBody User user){
		userService.updateUser(id, user);
		return ResponseEntity.ok().build();
	}
}
