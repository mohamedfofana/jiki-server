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
			return new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("KO", null, "User  "+ user.getUsername() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("OK", dbUser, "User created"), HttpStatus.OK);
	}
	
	
	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody User user) {
		final User dbUser = userService.update(user);
		if (dbUser == null) {
			return new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("KO", null, "Unable to uppdate user "+ user.getUsername() + "."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("OK", dbUser, "User updated"), HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public List<User> findAll(){
		return userService.findAll();
	}

	@GetMapping("/{id}")
	public User findById(@PathVariable("id") Long id){
		return userService.findById(id);
	}
	
	@GetMapping("/team/{id}")
	public List<User> findByTeam(@PathVariable("id") Long id){
		return userService.findByTeam(id);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		final boolean status = userService.deleteById(id);
		if(status) {
			return new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("OK", null, "User deleted"), HttpStatus.OK);
		}
		return  new ResponseEntity<CustomResponseType<User>>(new CustomResponseType<User>("KO", null, "Unable to delete user with id = "+ id + "."), HttpStatus.CONFLICT);
	}

}
