package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.enums.UserStatusEnum;
import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.helpers.TimeHelper;
import com.kodakro.jiki.model.User;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.IUserRepository;
import com.kodakro.jiki.repository.mapper.UserRowMapper;
import com.kodakro.jiki.repository.request.AbstractUserRequest;

@Repository
public class UserRepository extends AbstractUserRequest implements IGenericRepository<User>, IUserRepository {
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.user.insert}")
	private String sqlInsert;
	
	@Value("${sql.user.update}")
	private String sqlUpdate;
	
	@Value("${sql.user.delete}")
	private String sqlDelete;
	
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
	
	@Override
	public List<User> findByTeam(Long id) {
		final String whereSql= " WHERE US.TEAM_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<User> users = jdbcTemplate.query(getNoJointSelect(whereSql), param, types,
				new UserRowMapper());
		
		return users;
	}

	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
	}
	
	public Optional<User> findOneByUsername(String username) {
		final String whereSql = " WHERE US.USERNAME =?";
		Object[] param = {username};
		int[] types = {Types.VARCHAR};
		User user;
		user = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new UserRowMapper());
		
		return Optional.ofNullable(user);
		
	}
	
	@Override
	public boolean deleteById(Long id) {
		Optional<User> user = exists(id);
		 Object[] userId = new Object[] {id};
		if (user.isPresent()) {
			return jdbcTemplate.update(sqlDelete, userId)==1;
		}
		return false;
	}

	@Override
	public boolean update(User user) {
		Object[] param = { user.getFirstname(), 
				user.getLastname(), 
				user.getUsername(), 
				user.getEmail(), 
				user.getJobTitle(), 
				user.getRole(), 
				user.getStatus(), 
				user.getTeam().getId(),
				TimeHelper.timestampNow(),
				user.getId()};
		return jdbcTemplate.update(sqlUpdate, param)==1;
	}

	@Override
	public User create(User user) {
		user.setId(maxId()+1);
		user.setStatus(UserStatusEnum.ACTIVE.toString());
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, user.getId());
			ps.setLong(2, user.getTeam().getId());
			ps.setString(3, user.getFirstname());
			ps.setString(4, user.getLastname());
			ps.setString(5, user.getUsername());
			ps.setString(6, user.getEmail());
			ps.setString(7, user.getPassword());
			ps.setString(8, user.getJobTitle());
			ps.setString(9, user.getRole());
			ps.setString(10, user.getStatus());
			ps.setTimestamp(11, TimeHelper.timestampNow());
			return ps;
		});
		return user;
	}

	@Override
	public Optional<User> exists(Long id) {
		final String whereSql= " WHERE US.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(getNoJointSelect(whereSql), param, types,
					new UserRowMapper());
		}catch (EmptyResultDataAccessException e) {
			logger.info("Unable to find User " + id);
			throw new ResourceNotFoundException("exists", "User", "id", id);
		}
		return Optional.ofNullable(user);
	}

}
