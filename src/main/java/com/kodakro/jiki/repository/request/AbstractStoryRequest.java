package com.kodakro.jiki.repository.request;

public abstract class AbstractStoryRequest {
	protected final static String TABLE = "T_STORY ST";
	protected static final String SELECT_SQL = "SELECT ";
	protected static final String SELECT_MAX_ID = "SELECT MAX(ID) ";
	public static final String SIMPLE_COLUMNS_SQL = " "
			+ "ST.ID AS ID_ST,"
			+ "ST.REPORTER_ID AS REPORTER_ID_ST,"
			+ "ST.SPRINT_ID AS SPRINT_ID_ST,"
			+ "ST.PROJECT_ID AS PROJECT_ID_ST,"
			+ "ST.BACKLOG_ID AS BACKLOG_ID_ST,"
			+ "ST.SHORT_TITLE AS SHORT_TITLE_ST,"
			+ "ST.TITLE AS TITLE_ST,"
			+ "ST.ASSIGNED_TEAM_ID AS ASSIGNED_TEAM_ID_ST,"
			+ "ST.ASSIGNED_USER_ID AS ASSIGNED_USER_ID_ST,"
			+ "ST.DESCRIPTION AS DESCRIPTION_ST,"
			+ "ST.TYPE AS TYPE_ST," 
			+ "ST.SCOPE AS SCOPE_ST," 
			+ "ST.STATUS AS STATUS_ST,"
			+ "ST.PRIORITY AS PRIORITY_ST,"
			+ "ST.WORKFLOW AS WORKFLOW_ST," 
			+ "ST.STORY_POINTS AS STORY_POINTS_ST ,"
			+ "ST.BUSINESS_VALUE AS BUSINESS_VALUE_ST," 
			+ "ST.APPLI_VERSION AS APPLI_VERSION_ST,"  
			+ "ST.CREATION_DATE AS CREATION_DATE_ST,"
			+ "ST.UPDATE_DATE AS UPDATE_DATE_ST ,"
			+ "ST.START_DATE AS START_DATE_ST ,"
			+ "ST.END_DATE AS END_DATE_ST ,"
			+ "ST.ESTIMATED_END_DATE AS ESTIMATED_END_DATE_ST  \n";	
	
	public static final String COLUMNS_SQL = " "
			+ "ST.ID AS ID_ST,"
			+ "ST.REPORTER_ID AS REPORTER_ID_ST,"
			+ "ST.SPRINT_ID AS SPRINT_ID_ST,"
			+ "ST.PROJECT_ID AS PROJECT_ID_ST,"
			+ "ST.BACKLOG_ID AS BACKLOG_ID_ST,"
			+ "ST.SHORT_TITLE AS SHORT_TITLE_ST,"
			+ "ST.TITLE AS TITLE_ST,"
			+ "ST.ASSIGNED_TEAM_ID AS ASSIGNED_TEAM_ID_ST,"
			+ "ST.ASSIGNED_USER_ID AS ASSIGNED_USER_ID_ST,"
			+ "ST.DESCRIPTION AS DESCRIPTION_ST,"
			+ "ST.TYPE AS TYPE_ST," 
			+ "ST.SCOPE AS SCOPE_ST," 
			+ "ST.STATUS AS STATUS_ST,"
			+ "ST.PRIORITY AS PRIORITY_ST,"
			+ "ST.WORKFLOW AS WORKFLOW_ST," 
			+ "ST.STORY_POINTS AS STORY_POINTS_ST ,"
			+ "ST.BUSINESS_VALUE AS BUSINESS_VALUE_ST," 
			+ "ST.APPLI_VERSION AS APPLI_VERSION_ST,"  
			+ "ST.CREATION_DATE AS CREATION_DATE_ST,"
			+ "ST.UPDATE_DATE AS UPDATE_DATE_ST ,"
			+ "ST.START_DATE AS START_DATE_ST ,"
			+ "ST.END_DATE AS END_DATE_ST ,"
			+ "ST.ESTIMATED_END_DATE AS ESTIMATED_END_DATE_ST \n";	
	
	protected final String FROM_SQL="FROM "+TABLE;
	protected static final String JOIN_SPRINT =" INNER JOIN T_SPRINT SP_ASS ON SP_ASS.ID = ST.SPRINT_ID ";
	protected static final String LEFT_JOIN_SPRINT =" LEFT JOIN T_SPRINT SP_ASS ON SP_ASS.ID = ST.SPRINT_ID ";
	protected static final String JOIN_PROJECT =" INNER JOIN T_PROJECT PR_ASS ON PR_ASS.ID = ST.PROJECT_ID ";
	protected static final String JOIN_REPORTER =" INNER JOIN T_USER US_RE ON US_RE.ID = ST.REPORTER_ID ";
	protected static final String JOIN_BACKLOG =" INNER JOIN T_BACKLOG BA_ASS ON BA_ASS.ID = ST.BACKLOG_ID ";
	protected static final String LEFT_JOIN_BACKLOG =" LEFT JOIN T_BACKLOG BA_ASS ON BA_ASS.ID = ST.BACKLOG_ID ";
	protected static final String LEFT_JOIN_ASSIGNED_TEAM =" LEFT JOIN T_TEAM TE_ASS ON TE_ASS.ID = ST.ASSIGNED_TEAM_ID ";
	protected static final String LEFT_JOIN_ASSIGNED_USER =" LEFT JOIN T_USER US_ASS ON US_ASS.ID = ST.ASSIGNED_USER_ID ";
	
	protected String getMaxId() {
		StringBuilder sb = new StringBuilder(SELECT_MAX_ID);
		sb.append(FROM_SQL);	
		
		return sb.toString();
	}
	protected String getExists(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(SIMPLE_COLUMNS_SQL);
		sb.append(FROM_SQL);	
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	protected String getJoinSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);	
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(LEFT_JOIN_SPRINT);
		sb.append(LEFT_JOIN_BACKLOG);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	
	protected String getJoinSelectBacklog(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_BACKLOG);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(JOIN_SPRINT);
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	protected String getJoinSelectExists(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_SPRINT);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		sb.append(LEFT_JOIN_BACKLOG);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	
	protected String getJoinSelectSprint(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_SPRINT);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		sb.append(LEFT_JOIN_BACKLOG);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	
	protected String getJoinSelectOnProjectBacklog(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_BACKLOG);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		sb.append(LEFT_JOIN_SPRINT);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	
	protected String getJoinSelectOnProject(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_BACKLOG);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		sb.append(JOIN_PROJECT);
		sb.append(JOIN_REPORTER);
		sb.append(LEFT_JOIN_SPRINT);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	protected String getJoinSelectProjectSprint(String whereProject, String whereSprint) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractSprintRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractBacklogRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_PROJECT);
		if (whereSprint != null) {
			sb.append(whereSprint);
		}
		sb.append(JOIN_SPRINT);
		if (whereProject != null) {
			sb.append(whereProject);
		}
		sb.append(JOIN_REPORTER);
		sb.append(JOIN_BACKLOG);
		sb.append(LEFT_JOIN_ASSIGNED_TEAM);
		sb.append(LEFT_JOIN_ASSIGNED_USER);
		return sb.toString();
	}
	
	
	
}
