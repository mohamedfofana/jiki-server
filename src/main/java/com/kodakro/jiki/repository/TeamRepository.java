package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.mapper.TeamRowMapper;
import com.kodakro.jiki.repository.request.AbstractTeamRequest;

@Repository
public class TeamRepository extends AbstractTeamRequest implements IGenericRepository<Team> {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.team.insert}")
	private String sqlInsert;
	
	@Value("${sql.team.update}")
	private String sqlUpdate;
	
	@Value("${sql.team.delete}")
	private String sqlDelete;
	
	@Override
	public List<Team> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new TeamRowMapper());
	}
   
	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
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
	public boolean deleteById(Long id) {
		Optional<Team> team = exists(id);
		Object[] teamId = new Object[] {id};
		if (team.isPresent()) {
			return jdbcTemplate.update(sqlDelete, teamId)==1;
		}
		return false;
	}

	@Override
	public boolean update(Team team) {
		Object[] param = { team.getName(), team.getStatus(), team.getUpdateDate(), team.getId()};
		return jdbcTemplate.update(sqlUpdate, param) == 1;
	}

	@Override
	public Team create(Team team) {
		team.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, team.getId());
			ps.setString(2, team.getName());
			ps.setString(3, team.getStatus());
			ps.setTimestamp(4, team.getCreationDate());
			return ps;
		});
		return team;
	}

	@Override
	public Optional<Team> exists(Long id) {
		final String whereSql= " WHERE TE.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Team team = null;
		try {
			team = jdbcTemplate.queryForObject(getExists(whereSql), param, types,
					new TeamRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no entity found
		}
		return Optional.ofNullable(team);
	}
}