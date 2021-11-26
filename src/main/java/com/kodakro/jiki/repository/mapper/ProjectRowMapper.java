package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;

public class ProjectRowMapper extends AbstractRowMapper implements RowMapper<Project>{
	@Override
	public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
		Project project = new Project();
		project.setId(rs.getLong("ID_PR"));
		project.setName(rs.getString("NAME_PR"));
		project.setDescription(rs.getString("DESCRIPTION_PR"));
		project.setStatus(rs.getString("STATUS_PR"));
		project.setCreationDate(rs.getDate("CREATION_DATE_PR"));
		project.setUpdateDate(rs.getDate("UPDATE_DATE_PR"));
		project.setEndDate(rs.getDate("END_DATE_PR"));
		if (isIdColumn(rs,"ID_BA")) {
			Backlog backlog = new BacklogRowMapper().mapRow(rs, rowNum);
			project.setBacklog(backlog);
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
