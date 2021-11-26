package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	public Optional<Backlog> findById(Long id) {
		final String whereSql= " WHERE BA.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Backlog backlog = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
				new BacklogRowMapper());

		return Optional.ofNullable(backlog);
	}

	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM "+ TABLE +" WHERE BA.ID=?";
		Optional<Backlog> backlog = findById(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (backlog.isPresent())
			jdbcTemplate.update(sql, param, types);
	}

	@Override
	public void update(Backlog t) {
		final String sql = "UPDATE PROJECT  SET TITLE='?', DESCRIPTION='?', STATUS='?', CREATION_DATE=?, "
				+ " END_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { t.getTitle(), t.getDescription(), t.getStatus(), t.getCreationDate(), t.getUpdateDate()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public Backlog create(Backlog t) {
		final String sql = "INSERT INTO "+TABLE+"(TITLE, DESCRIPTION, STATUS, CREATION_DATE, UPDATE_DATE) VALUES (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setString(1, t.getTitle());
			ps.setString(2, t.getDescription());
			ps.setString(3, t.getStatus());
			ps.setDate(4, t.getCreationDate());
			ps.setDate(5, t.getUpdateDate());
			return ps;
		}, keyHolder);
		t.setId((long) keyHolder.getKey());
		return t;
	}
}
