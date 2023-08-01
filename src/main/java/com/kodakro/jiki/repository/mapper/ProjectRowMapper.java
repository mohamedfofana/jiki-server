package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Team;

public class ProjectRowMapper extends AbstractRowMapper implements RowMapper<Project>{
	@Override
	public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
		Project project = new Project();
		project.setId(rs.getLong("ID_PR"));
		project.setShortname(rs.getString("SHORTNAME_PR"));
		project.setName(rs.getString("NAME_PR"));
		project.setDescription(rs.getString("DESCRIPTION_PR"));
		project.setStatus(rs.getString("STATUS_PR"));
		project.setCreationDate(rs.getTimestamp("CREATION_DATE_PR"));
		project.setUpdateDate(rs.getTimestamp("UPDATE_DATE_PR"));
		project.setEndDate(rs.getTimestamp("END_DATE_PR"));
		if (isIdColumn(rs,"ID_BA")) {
			Backlog backlog = new BacklogRowMapper().mapRow(rs, rowNum);
			project.setBacklog(backlog);
		}
		if (isIdColumn(rs,"ID_TE")) {
			Team team = new TeamRowMapper().mapRow(rs, rowNum);
			project.setTeam(team);
		}
		return project;
	}
	public Project mapRowAssigned(ResultSet rs, int rowNum) throws SQLException {
		Project project = new Project();
		project.setId(rs.getLong("ID_PR_ASS"));
		project.setName(rs.getString("NAME_PR_ASS"));
		project.setDescription(rs.getString("DESCRIPTION_PR_ASS"));
		
//		if (rs.findColumn("ID_TE")>=0) {
//			Team team = new TeamRowMapper().mapRow(rs, rowNum);
//			project.setTeam(team);
//		}
		return project;
	}
}
