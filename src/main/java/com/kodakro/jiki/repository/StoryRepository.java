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

import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.repository.mapper.StoryRowMapper;
import com.kodakro.jiki.repository.request.AbstractStoryRequest;

@Repository
public class StoryRepository extends AbstractStoryRequest  implements IGenericRepository<Story> {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Story> findAll() {
		return jdbcTemplate.query(getJoinSelect(null), new StoryRowMapper());
	}

	@Override
	public Optional<Story> findById(Long id) {
		final String whereSql= " AND ST.ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Story story = null;
		try {
			story = jdbcTemplate.queryForObject(getJoinSelect(whereSql), param, types,
					new StoryRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no Story found
		}
		return Optional.ofNullable(story);
	}
	
	public List<Story> findByReporterId(Long id) {
		final String whereSql= " AND ST.REPORTER_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
				new StoryRowMapper());

		return stories;
	}
	
	public List<Story> findByProjectId(Long id) {
		final String whereSql= " AND ST.PROJECT_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
				new StoryRowMapper());
		
		return stories;
	}

	public List<Story> findStoriesOnBacklogsByProjectId(Long id) {
		final String whereSql= " AND ST.PROJECT_ID =? AND BA_ASS.ID IS NOT NULL";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectOnBacklogsProject(whereSql), param, types,
				new StoryRowMapper());
		return stories;
	}
	
	public List<Story> findByProjectIdAndSprintId(Long projectId, Long sprintId) {
		final String whereProject= " AND ST.PROJECT_ID =?";
		Object[] param = {projectId, sprintId};
		int[] types = {Types.INTEGER, Types.INTEGER};
		final String whereSprint= " AND ST.SPRINT_ID =?";
		List<Story> stories = jdbcTemplate.query(getJoinSelectProjectSprint(whereProject, whereSprint), param, types,
				new StoryRowMapper());
		
		return stories;
	}
	
	public List<Story> findByProjectIdAndCurrentSprint(Long id) {
		final String whereSql= " AND ST.PROJECT_ID =? AND SP_ASS.STATUS='RUNNING'";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelect(whereSql), param, types,
				new StoryRowMapper());
		
		return stories;
	}

	public List<Story> findBySprintId(Long id) {
		final String whereSql= " AND ST.SPRINT_ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		List<Story> stories = jdbcTemplate.query(getJoinSelectSprint(whereSql), param, types,
				new StoryRowMapper());
		
		return stories;
	}

	@Override
	public void deleteById(Long id) {
		final String sql = "DELETE * FROM "+TABLE+" WHERE ST.ID=?";
		Optional<Story> story = findById(id);
		Object[] param = {id};
		int[] types = {Types.BIGINT};
		if (story.isPresent())
			jdbcTemplate.update(sql, param, types);
	}

	@Override
	public void update(Story story) {
		final String sql = "UPDATE "+TABLE+" SET TITLE='?', DESCRIPTION='?', TYPE=?, STATUS='?', PRIORITY=?, WORKFLOW='?', CREATION_DATE=?, UPDATE_DATE=?, "
				+ " STORY_POINTS=?, BUSINESS_VALUE=?, APPLI_VERSION='?', START_DATE=?, END_DATE=?, ESTIMATED_END_DATE=? "
				+ " WHERE ID=?";
		Object[] param = { story.getTitle(), story.getDescription(), story.getType(), story.getStatus(), story.getPriority(), story.getCreationDate(), story.getUpdateDate(),
				story.getStoryPoints(), story.getBusinessValue(), story.getAppliVersion(), story.getStartDate(), story.getEndDate(),
				story.getEstimatedEndDate(),  story.getId()};
		jdbcTemplate.update(sql, param);
	}

	public void updateStatus(Story story) {
		final String sql = "UPDATE "+TABLE+" SET STATUS=? "
				+ " WHERE ID=?";
		Object[] param = { story.getStatus(), story.getId()};
		jdbcTemplate.update(sql, param);
	}

	public void updateSprintAndBacklog(Story story) {
		final String sql = "UPDATE "+TABLE+" SET BACKLOG_ID=?, SPRINT_ID=? "
				+ " WHERE ID=?";
		Long backlogId = story.getBacklog()!=null? story.getBacklog().getId():null;
		Long sprintId = story.getSprint()!=null? story.getSprint().getId():null;
		Object[] param = { backlogId, sprintId, story.getId()};
		int[] types   = {   backlogId==null?Types.NULL:Types.BIGINT, 
							sprintId==null?Types.NULL:Types.BIGINT, 
									Types.BIGINT};
		jdbcTemplate.update(sql, param, types);
	}
	
	@Override
	public Story create(Story story) {
		final String sql = "INSERT INTO "+TABLE+"(REPORTER_ID, SPRINT_ID, BACKLOG_ID, ASSIGNED_TEAM_ID, ASSIGNED_USER_ID, TITLE, DESCRIPTION, TYPE, STATUS, PRIORITY, WORKFLOW, "
				+ "CREATION_DATE, UPDATE_DATE, STORY_POINTS, BUSINESS_VALUE, APPLI_VERSION, START_DATE, "
				+ "END_DATE, ESTIMATED_END_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sql);
			ps.setLong(1, story.getReporter().getId());
			ps.setLong(2, story.getSprint().getId());
			ps.setLong(3, story.getBacklog().getId());
			ps.setLong(4, story.getAssignedTeam().getId());
			ps.setLong(5, story.getAssignedUser().getId());
			ps.setString(6, story.getTitle());
			ps.setString(7, story.getDescription());
			ps.setString(8, story.getType());
			ps.setString(9, story.getStatus());
			ps.setString(10, story.getPriority());
			ps.setString(11, story.getWorkflow());
			ps.setDate(12, story.getCreationDate());
			ps.setDate(13, story.getUpdateDate());
			ps.setInt(14, story.getStoryPoints());
			ps.setInt(15, story.getBusinessValue());
			ps.setString(16, story.getAppliVersion());
			ps.setDate(17, story.getStartDate());
			ps.setDate(18, story.getEndDate());
			ps.setDate(19, story.getEstimatedEndDate());
			return ps;
		}, keyHolder);
		story.setId((long) keyHolder.getKey());
		return story;
	}

	
	
}
