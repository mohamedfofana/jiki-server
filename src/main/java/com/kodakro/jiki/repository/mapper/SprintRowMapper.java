package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.model.User;

public class SprintRowMapper extends AbstractRowMapper implements RowMapper<Sprint>{
	@Override
	public Sprint mapRow(ResultSet rs, int rowNum) throws SQLException {
		Sprint sprint = new Sprint();
		sprint.setId(rs.getLong("ID_SP"));
		sprint.setTitle(rs.getString("TITLE_SP"));
		sprint.setDescription(rs.getString("DESCRIPTION_SP"));
		sprint.setStatus(rs.getString("STATUS_SP"));
		sprint.setWorkflow(rs.getString("WORKFLOW_SP"));
		sprint.setCreationDate(rs.getTimestamp("CREATION_DATE_SP"));
		sprint.setUpdateDate(rs.getTimestamp("UPDATE_DATE_SP"));
		sprint.setStoryPoints(rs.getInt("STORY_POINTS_SP"));
		sprint.setBusinessValue(rs.getInt("BUSINESS_VALUE_SP"));
		sprint.setAppliVersion(rs.getString("APPLI_VERSION_SP"));
		sprint.setStartDate(rs.getTimestamp("START_DATE_SP"));
		sprint.setEndDate(rs.getTimestamp("END_DATE_SP"));
		sprint.setEstimatedEndDate(rs.getTimestamp("ESTIMATED_END_DATE_SP"));
		if (isIdColumn(rs,"ID_PR")){
			Project project = new ProjectRowMapper().mapRow(rs, rowNum);
			sprint.setProject(project);
		}
		if (isIdColumn(rs,"ID_US_RE")) {
			User user = new UserRowMapper().mapRowReporter(rs, rowNum);
			sprint.setReporter(user);
		}
		if (isIdColumn(rs,"ID_BA")) {
			Backlog backlog = new BacklogRowMapper().mapRow(rs, rowNum);
			sprint.setBacklog(backlog);
		}
		return sprint;
	}
	
	public Sprint mapRowAssigned(ResultSet rs, int rowNum) throws SQLException {
		Sprint sprint = new Sprint();
		sprint.setId(rs.getLong("ID_SP_ASS"));
		sprint.setTitle(rs.getString("TITLE_SP_ASS"));
		sprint.setDescription(rs.getString("DESCRIPTION_SP_ASS"));
		sprint.setStatus(rs.getString("STATUS_SP_ASS"));
		sprint.setWorkflow(rs.getString("WORKFLOW_SP_ASS"));
		sprint.setCreationDate(rs.getTimestamp("CREATION_DATE_SP_ASS"));
		sprint.setUpdateDate(rs.getTimestamp("UPDATE_DATE_SP_ASS"));
		sprint.setStoryPoints(rs.getInt("STORY_POINTS_SP_ASS"));
		sprint.setBusinessValue(rs.getInt("BUSINESS_VALUE_SP_ASS"));
		sprint.setAppliVersion(rs.getString("APPLI_VERSION_SP_ASS"));
		sprint.setStartDate(rs.getTimestamp("START_DATE_SP_ASS"));
		sprint.setEndDate(rs.getTimestamp("END_DATE_SP_ASS"));
		sprint.setEstimatedEndDate(rs.getTimestamp("ESTIMATED_END_DATE_SP_ASS"));
//		if (rs.findColumn("ID_PR_ASS")>=0) {
//			Project project = new ProjectRowMapper().mapRow(rs, rowNum);
//			sprint.setProject(project);
//		}
//		if (rs.findColumn("ID_BA")>=0) {
//			Backlog backlog = new BacklogRowMapper().mapRow(rs, rowNum);
//			sprint.setBacklog(backlog);
//		}
		return sprint;
	}
}
