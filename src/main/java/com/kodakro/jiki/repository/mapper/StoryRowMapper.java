package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.model.User;

public class StoryRowMapper extends AbstractRowMapper implements RowMapper<Story>{
	@Override
	public Story mapRow(ResultSet rs, int rowNum) throws SQLException {
		Story story = new Story();
		story.setId(rs.getLong("ID_ST"));
		story.setTitle(rs.getString("TITLE_ST"));
		story.setDescription(rs.getString("DESCRIPTION_ST"));
		story.setType(rs.getString("TYPE_ST"));
		story.setStatus(rs.getString("STATUS_ST"));
		story.setPriority(rs.getString("PRIORITY_ST"));
		story.setWorkflow(rs.getString("WORKFLOW_ST"));
		story.setStoryPoints(rs.getInt("STORY_POINTS_ST"));
		story.setBusinessValue(rs.getInt("BUSINESS_VALUE_ST"));
		story.setAppliVersion(rs.getString("APPLI_VERSION_ST"));
		story.setCreationDate(rs.getDate("CREATION_DATE_ST"));
		story.setUpdateDate(rs.getDate("UPDATE_DATE_ST"));
		story.setStartDate(rs.getDate("START_DATE_ST"));
		story.setEndDate(rs.getDate("END_DATE_ST"));
		story.setEstimatedEndDate(rs.getDate("ESTIMATED_END_DATE_ST"));
		
		if (isIdColumn(rs,"ID_SP_ASS")) {
			Sprint sprint = new SprintRowMapper().mapRowAssigned(rs, rowNum);
			story.setSprint(sprint);
		}
		if (isIdColumn(rs,"ID_PR_ASS")) {
			Project project = new ProjectRowMapper().mapRowAssigned(rs, rowNum);
			story.setProject(project);
		}
		if (isIdColumn(rs,"ID_US_RE")) {
			User user = new UserRowMapper().mapRowReporter(rs, rowNum);
			story.setReporter(user);
		}
		if (isIdColumn(rs,"ID_BA_ASS")) {
			Backlog backlog = new BacklogRowMapper().mapRowAssigned(rs, rowNum);
			story.setBacklog(backlog);
		}
		if (isIdColumn(rs,"ID_US_ASS")) {
			User user = new UserRowMapper().mapRowAssigned(rs, rowNum);
			story.setAssignedUser(user);
		}
		if (isIdColumn(rs,"ID_TE_ASS")) {
			Team team = new TeamRowMapper().mapRowAssigned(rs, rowNum);
			story.setAssignedTeam(team);
		}
		return story;
	}
}
