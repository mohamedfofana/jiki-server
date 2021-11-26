package com.kodakro.jiki.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.model.User;
import com.kodakro.jiki.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

		@Autowired
		UserRepository userRepository;
		
		/**
		 * When a user tries to authenticate, this method receives the username, 
		 * searches the database for a record containing it, and (if found) returns an instance of User. 
		 * The properties of this instance (username and password) are then checked against the credentials passed by the user in the login request. 
		 * This last process is executed outside this class, by the Spring Security framework.
		 */
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			Objects.requireNonNull(username);
	        Optional<User> user = userRepository.findOneByUsername(username);
	        if(user.isPresent())
	        	return user.get();
	        throw new UsernameNotFoundException("L'utilisateur n'existe pas.");
		}
}
