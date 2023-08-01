package com.kodakro.jiki.repository.intrf;

import java.util.List;

import com.kodakro.jiki.model.User;

public interface IUserRepository {
	List<User> findByTeam(Long id);
}
