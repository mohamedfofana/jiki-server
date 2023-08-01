package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
	
//	@Cacheable(value="usersCache")
	public List<User> findAll(){
		return userRepository.findAll();
	}

	@Cacheable(value="usersCache",key="#id",unless="#result==null")
	public User findById(Long id){
		Optional<User> user= userRepository.findById(id);
		if (user.isPresent())
			return user.get();
		else
			return null;
	}
	
	@Cacheable(value="usersCache",key="#id",unless="#result==null")
	public List<User> findByTeam(Long id){
		List<User> users= userRepository.findByTeam(id);
		return users;
	}
	
	@CacheEvict(value="usersCache",key="#id")
	public boolean deleteById(Long id){
		return userRepository.deleteById(id);
	}

	public User update(User user) {
		if(user != null) {
			User dbUser= userRepository.exists(user.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", user.getId()));
			if (dbUser!=null) {
				if (user.getUsername() != null)
					dbUser.setUsername(user.getUsername());
				if (user.getFirstname() != null)
					dbUser.setFirstname(user.getFirstname());
				if (user.getLastname() != null)
					dbUser.setLastname(user.getLastname());
				if (user.getEmail() != null)
					dbUser.setEmail(user.getEmail());
				if (user.getStatus() != null)
					dbUser.setStatus(user.getStatus());
				if (user.getRole() != null)
					dbUser.setRole(user.getRole());
				if (user.getStatus() != null)
					dbUser.setStatus(user.getStatus());
				
				if (user.getTeam() != null)
					dbUser.setTeam(user.getTeam());

				userRepository.update(dbUser);
				return dbUser;
			}
		}
		return null;
	}
	
}
