package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Team;

public class TeamRowMapper implements RowMapper<Team>{
	@Override
	public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
		Team team = new Team();
		team.setId(rs.getLong("ID_TE"));
		team.setName(rs.getString("NAME_TE"));
		team.setStatus(rs.getString("STATUS_TE"));
		team.setCreationDate(rs.getTimestamp("CREATION_DATE_TE"));
		team.setUpdateDate(rs.getTimestamp("UPDATE_DATE_TE"));
		return team;
	}
	
	public Team mapRowAssigned(ResultSet rs, int rowNum) throws SQLException {
		Team team = new Team();
		team.setId(rs.getLong("ID_TE_ASS"));
		team.setName(rs.getString("NAME_TE_ASS"));
		return team;
	}
	
	public Team mapRowAssignedToProject(ResultSet rs, int rowNum) throws SQLException {
		Team team = new Team();
		team.setId(rs.getLong("ID_TE_PR_ASS"));
		team.setName(rs.getString("NAME_TE_PR_ASS"));
		return team;
	}
}
