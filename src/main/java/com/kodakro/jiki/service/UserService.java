package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.User;
import com.kodakro.jiki.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User register(User user) {
		Optional<User> dbUser = this.userRepository.findOneByUsername(user.getUsername());
		if (dbUser.isPresent()) {
			return null;
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.create(user);
	}
	
	@Cacheable(value="usersCache")
	public List<User> getUsers(){
		return userRepository.findAll();
	}

	@Cacheable(value="usersCache",key="#id",unless="#result==null")
	public User getUserById(Long id){
		Optional<User> user= userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}
	
	@CacheEvict(value="usersCache",key="#id")
	public void deleteUserById(Long id){
		userRepository.deleteById(id);
	}

	public void patchUser(Long id, User user) {
		User dbUser= userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		if (dbUser!=null) {
			if (user.getFirstname() != null)
				dbUser.setFirstname(user.getFirstname());
			if (user.getLastname() != null)
				dbUser.setLastname(user.getLastname());
			if (user.getTeam() != null)
				dbUser.setTeam(user.getTeam());
			if (user.getStatus() != null)
				dbUser.setStatus(user.getStatus());
			userRepository.update(dbUser);
		}
	}
	
	@CachePut(value="usersCache",key="#id")
	public void updateUser(Long id, User user){
		User dbUser =  userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		if (dbUser!=null) {
			dbUser.setFirstname(user.getFirstname());
			dbUser.setLastname(user.getLastname());
			dbUser.setTeam(user.getTeam());
			dbUser.setStatus(user.getStatus());
			userRepository.update(dbUser);
		}
	}
	
}
