package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.enums.RoleEnum;
import com.kodakro.jiki.enums.StatusEnum;
import com.kodakro.jiki.model.User;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.IUserRepository;
import com.kodakro.jiki.repository.mapper.UserRowMapper;
import com.kodakro.jiki.repository.request.AbstractUserRequest;

@Repository
public class UserRepository extends AbstractUserRequest implements IGenericRepository<User>, IUserRepository {
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

	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
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
	public boolean deleteById(Long id) {
		final String sql = "DELETE FROM T_USER WHERE ID=?";
		Optional<User> user = exists(id);
		 Object[] userId = new Object[] {id};
		if (user.isPresent()) {
			return jdbcTemplate.update(sql, userId)==1;
		}
		return false;
	}

	@Override
	public boolean update(User user) {
		final String sql = "UPDATE T_USER SET FIRSTNAME=?, LASTNAME=?, USERNAME=?, EMAIL=?, ROLE=?, STATUS=?, TEAM_ID=?, PROJECT_ID=?, UPDATE_DATE=?"
				+ " WHERE ID=?";
		Object[] param = { user.getFirstname(), user.getLastname(), user.getUsername(), user.getEmail(), user.getRole(), user.getStatus(), user.getTeam().getId(), user.getProject().getId(), LocalDate.now(), user.getId()};
		return jdbcTemplate.update(sql, param)==1;
	}

	@Override
	public User create(User user) {
		final String sql = "INSERT INTO T_USER (ID, TEAM_ID, PROJECT_ID,FIRSTNAME, LASTNAME, USERNAME, EMAIL, PASSWORD, ROLE, STATUS, CREATION_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		user.setId(maxId()+1);
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, user.getId());
			ps.setLong(2, user.getTeam().getId());
			ps.setLong(3, user.getProject().getId());
			ps.setString(4, user.getFirstname());
			ps.setString(5, user.getLastname());
			ps.setString(6, user.getUsername());
			ps.setString(7, user.getEmail());
			ps.setString(8, user.getPassword());
			ps.setString(9, user.getRole()==null? RoleEnum.USER.toString():user.getRole());
			ps.setString(10, user.getStatus()==null? StatusEnum.ACTIVE.toString():user.getStatus());
			ps.setTimestamp(11, user.getCreationDate());
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
			user = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new UserRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no user found
		}
		return Optional.ofNullable(user);
	}

}
