package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.enums.RoleEnum;
import com.kodakro.jiki.enums.StatusEnum;
import com.kodakro.jiki.model.User;
import com.kodakro.jiki.repository.mapper.UserAuthRowMapper;
import com.kodakro.jiki.repository.mapper.UserRowMapper;
import com.kodakro.jiki.repository.request.AbstractUserRequest;

@Repository
public class UserRepository extends AbstractUserRequest implements IGenericRepository<User> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new UserRowMapper());
	}

	@Override
	public Optional<User> findById(Long id) {
		final String whereSql= " AND US.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		User user = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
				new UserRowMapper());

		return Optional.ofNullable(user);
	}

	public Optional<User> findOneByUsername(String username) {
		final String whereSql = " WHERE US.USERNAME =?";
		Object[] param = {username};
		int[] types = {Types.VARCHAR};
		User user;
		try{
			user = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new UserRowMapper());
		}catch(IncorrectResultSizeDataAccessException e) {
			return Optional.empty();  
		}	
		return Optional.ofNullable(user);
		
	}
	
	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM T_USER WHERE ID=?";
		Optional<User> user = findById(id);
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		if (user.isPresent())
			jdbcTemplate.update(sql, param, types);
	}

	@Override
	public void update(User user) {
		final String sql = "UPDATE T_USER SET FIRSTNAME='?', LASTNAME='?', USERNAME='?', ROLE=?, STATUS='?', UPDATE_DATE=?"
				+ " WHERE ID=?";
		Object[] param = { user.getFirstname(), user.getLastname(), user.getUsername(), user.getStatus(), user.getUpdateDate()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public User create(User user) {
		final String sql = "INSERT INTO T_USER (ID, TEAM_ID, PROJECT_ID,FIRSTNAME, LASTNAME, USERNAME, PASSWORD, ROLE, STATUS, CREATION_DATE) VALUES (?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, user.getId());
			ps.setLong(2, user.getTeam().getId());
			ps.setLong(3, user.getProject().getId());
			ps.setString(4, user.getFirstname());
			ps.setString(5, user.getLastname());
			ps.setString(6, user.getUsername());
			ps.setString(7, user.getPassword());
			ps.setString(8, user.getRole()==null? RoleEnum.USER.toString():user.getRole());
			ps.setString(9, user.getStatus()==null? StatusEnum.ACTIVE.toString():user.getStatus());
			ps.setDate(10, user.getCreationDate());
			return ps;
		});
		return user;
	}

	@Override
	public Optional<User> exists(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
