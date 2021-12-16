package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.repository.mapper.TeamRowMapper;
import com.kodakro.jiki.repository.request.AbstractTeamRequest;

@Repository
public class TeamRepository extends AbstractTeamRequest implements IGenericRepository<Team> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	private final static String TABLE = "T_TEAM";
	@Override
	public List<Team> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new TeamRowMapper());
	}

	@Override
	public Optional<Team> findById(Long id) {
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Team team = null;
		try{
			team = jdbcTemplate.queryForObject(getJoinSelect(null), param, types,
					new TeamRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// Log no Backlog found
		}
		return Optional.ofNullable(team);
	}

	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM "+TABLE+" WHERE ID=?";
		Optional<Team> team = findById(id);
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		if (team.isPresent())
			jdbcTemplate.update(sql, param, types);
	}

	@Override
	public void update(Team team) {
		final String sql = "UPDATE "+TABLE+" SET NAME='?', STATUS='?', CREATION_DATE=?, UPDATE_DATE=?"
				+ " WHERE ID=?";
		Object[] param = { team.getName(), team.getStatus(), team.getCreationDate(), team.getUpdateDate()};
		jdbcTemplate.update(sql, param);
	}

	@Override
	public Team create(Team team) {
		final String sql = "INSERT INTO "+TABLE+"(TITLE, STATUS, CREATION_DATE, UPDATE_DATE) VALUES (?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setString(1, team.getName());
			ps.setString(2, team.getStatus());
			ps.setDate(3, team.getCreationDate());
			ps.setDate(4, team.getUpdateDate());
			return ps;
		}, keyHolder);
		team.setId((long) keyHolder.getKey());
		return team;
	}

	@Override
	public Optional<Team> exists(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
