package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.User;

public class UserAuthRowMapper extends AbstractRowMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("USERNAME_US"));
		user.setPassword(rs.getString("PASSWORD_US"));
		user.setRole(rs.getString("ROLE_US"));
		return user;
	}
	
}
