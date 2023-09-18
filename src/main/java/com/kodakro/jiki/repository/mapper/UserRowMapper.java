package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.model.User;

public class UserRowMapper extends AbstractRowMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("ID_US"));
		user.setFirstname(rs.getString("FIRSTNAME_US"));
		user.setLastname(rs.getString("LASTNAME_US"));
		user.setUsername(rs.getString("USERNAME_US"));
		user.setPassword(rs.getString("PASSWORD_US"));
		user.setEmail(rs.getString("EMAIL_US"));
		user.setStatus(rs.getString("STATUS_US"));
		user.setJobTitle(rs.getString("JOBTITLE_US"));
		user.setRole(rs.getString("ROLE_US"));
		user.setCreationDate(rs.getTimestamp("CREATION_DATE_US"));
		user.setUpdateDate(rs.getTimestamp("UPDATE_DATE_US"));
		if (isIdColumn(rs, "ID_TE")) {
			Team team = new TeamRowMapper().mapRow(rs, rowNum);
			user.setTeam(team);
		}
		/*if (isIdColumn(rs, "ID_PR_ASS")) {
			Project project = new ProjectRowMapper().mapRowAssigned(rs, rowNum);
			user.setProject(project);
			if (isIdColumn(rs,"ID_BA_ASS")) {
				Backlog backlog = new BacklogRowMapper().mapRowAssigned(rs, rowNum);
				project.setBacklog(backlog);
			}
		}*/
		return user;
	}
	
	public User mapRowAuth(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setUsername(rs.getString("USERNAME_US"));
		user.setPassword(rs.getString("PASSWORD_US"));
		user.setJobTitle(rs.getString("JOBTITLE_US"));
		user.setRole(rs.getString("ROLE_US"));
		return user;
	}
	
	public User mapRowReporter(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("ID_US_RE"));
		user.setFirstname(rs.getString("FIRSTNAME_US_RE"));
		user.setLastname(rs.getString("LASTNAME_US_RE"));
		user.setEmail(rs.getString("EMAIL_US_RE"));
		user.setJobTitle(rs.getString("JOBTITLE_US_RE"));
		user.setRole(rs.getString("ROLE_US_RE"));
		return user;
	}
	public User mapRowAssigned(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("ID_US_ASS"));
		user.setFirstname(rs.getString("FIRSTNAME_US_ASS"));
		user.setLastname(rs.getString("LASTNAME_US_ASS"));
		user.setEmail(rs.getString("EMAIL_US_ASS"));
		user.setJobTitle(rs.getString("JOBTITLE_US_ASS"));
		user.setRole(rs.getString("ROLE_US_ASS"));
		return user;
	}
}
