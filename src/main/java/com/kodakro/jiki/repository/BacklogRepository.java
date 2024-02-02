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

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.mapper.BacklogRowMapper;
import com.kodakro.jiki.repository.request.AbstractBacklogRequest;

@Repository
public class BacklogRepository extends AbstractBacklogRequest implements IGenericRepository<Backlog> {
	private static final Logger logger = LoggerFactory.getLogger(BacklogRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.backlog.insert}")
	private String sqlInsert;
	
	@Value("${sql.backlog.update}")
	private String sqlUpdate;
	
	@Value("${sql.backlog.delete}")
	private String sqlDelete;
	
	@Override
	public List<Backlog> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new BacklogRowMapper());
	}
	
	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		
		return maxId != null? maxId:1;
	}

	@Override
	public Optional<Backlog> findById(Long id) {
		final String whereSql= " WHERE BA.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Backlog backlog = null;
		try {
			backlog = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types, new BacklogRowMapper());
		} catch (EmptyResultDataAccessException ex) {
			logger.info("Unable to find backlog " + id);
			throw new ResourceNotFoundException("findById", "Backlog", "id", id);
		}
		
		return Optional.ofNullable(backlog);
	}

	@Override
	public boolean deleteById(Long id) {
		Optional<Backlog> backlog = findById(id);
		Object[] backlogId = new Object[] {id};
		if (backlog.isPresent())
			return jdbcTemplate.update(sqlDelete, backlogId)==1;
		
		logger.info("Unable to delete backlog " + id);
		return false;
	}

	@Override
	public boolean update(Backlog t) {
		Object[] param = { t.getTitle(), t.getDescription(), t.getStatus(), t.getUpdateDate()};
		return jdbcTemplate.update(sqlUpdate, param)==1;
	}

	@Override
	public Backlog create(Backlog backlog) {
		backlog.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
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
		backlog = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
				new BacklogRowMapper());
		
		return Optional.ofNullable(backlog);
	}
}
