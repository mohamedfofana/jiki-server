package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.repository.mapper.BacklogRowMapper;
import com.kodakro.jiki.repository.request.AbstractBacklogRequest;

@Repository
public class BacklogRepository extends AbstractBacklogRequest implements IGenericRepository<Backlog> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Backlog> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new BacklogRowMapper());
	}
	
	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
	}

	@Override
	public Optional<Backlog> findById(Long id) {
		final String whereSql= " WHERE BA.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Backlog backlog = null;
		try{
			backlog = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
				new BacklogRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// Log no Backlog found
		}
		return Optional.ofNullable(backlog);
	}

	@Override
	public boolean deleteById(Long id) {
		final String sql = "DELETE FROM T_BACKLOG WHERE ID=?";
		Optional<Backlog> backlog = findById(id);
		Object[] backlogId = new Object[] {id};
		if (backlog.isPresent())
			return jdbcTemplate.update(sql, backlogId)==1;
		return false;
	}

	@Override
	public boolean update(Backlog t) {
		final String sql = "UPDATE T_BACKLOG  SET TITLE='?', DESCRIPTION='?', STATUS='?', UPDATE_DATE=?, "
				+ " WHERE ID=?";
		Object[] param = { t.getTitle(), t.getDescription(), t.getStatus(), t.getUpdateDate()};
		return jdbcTemplate.update(sql, param)==1;
	}

	@Override
	public Backlog create(Backlog backlog) {
		final String sql = "INSERT INTO T_BACKLOG (ID, TITLE, DESCRIPTION, STATUS, CREATION_DATE) VALUES (?,?,?,?,?)";
		backlog.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, backlog.getId());
			ps.setString(2, backlog.getTitle());
			ps.setString(3, backlog.getDescription());
			ps.setString(4, backlog.getStatus());
			ps.setTimestamp(5, backlog.getCreationDate());
			return ps;
		});

		return backlog;
	}

	@Override
	public Optional<Backlog> exists(Long id) {
		final String whereSql= " WHERE BA.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Backlog backlog = null;
		try {
			backlog = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new BacklogRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no enity found
		}
		return Optional.ofNullable(backlog);
	}
}
