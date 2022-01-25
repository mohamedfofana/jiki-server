package com.kodakro.jiki.repository.request;

public abstract class AbstractSprintRequest {
	protected final static String TABLE = "T_SPRINT SP";
	protected static final String SELECT_SQL = "SELECT ";
	protected static final String SELECT_MAX_ID = "SELECT MAX(ID) ";
	public static final String COLUMNS_SQL = " "
		+ "SP.ID AS ID_SP," 
		+ "SP.REPORTER_ID AS REPORTER_ID_SP," 
		+ "SP.PROJECT_ID AS PROJECT_ID_SP,"
		+ "SP.TITLE AS TITLE_SP," 
		+ "SP.DESCRIPTION AS DESCRIPTION_SP,"
		+ "SP.STATUS AS STATUS_SP,"
		+ "SP.WORKFLOW AS WORKFLOW_SP," 
		+ "SP.CREATION_DATE AS CREATION_DATE_SP,"
		+ "SP.UPDATE_DATE AS UPDATE_DATE_SP,"
		+ "SP.STORY_POINTS AS STORY_POINTS_SP,"
		+ "SP.BUSINESS_VALUE AS BUSINESS_VALUE_SP," 
		+ "SP.APPLI_VERSION AS APPLI_VERSION_SP," 
		+ "SP.START_DATE AS START_DATE_SP," 
		+ "SP.END_DATE AS END_DATE_SP," 
		+ "SP.ESTIMATED_END_DATE AS ESTIMATED_END_DATE_SP \n";
	public static final String ASSIGNED_COLUMNS_SQL = " "
			+ "SP_ASS.ID AS ID_SP_ASS," 
			+ "SP_ASS.REPORTER_ID AS REPORTER_ID_SP_ASS," 
			+ "SP_ASS.PROJECT_ID AS PROJECT_ID_SP_ASS,"
			+ "SP_ASS.TITLE AS TITLE_SP_ASS," 
			+ "SP_ASS.DESCRIPTION AS DESCRIPTION_SP_ASS,"
			+ "SP_ASS.STATUS AS STATUS_SP_ASS,"
			+ "SP_ASS.WORKFLOW AS WORKFLOW_SP_ASS," 
			+ "SP_ASS.CREATION_DATE AS CREATION_DATE_SP_ASS,"
			+ "SP_ASS.UPDATE_DATE AS UPDATE_DATE_SP_ASS,"
			+ "SP_ASS.STORY_POINTS AS STORY_POINTS_SP_ASS,"
			+ "SP_ASS.BUSINESS_VALUE AS BUSINESS_VALUE_SP_ASS," 
			+ "SP_ASS.APPLI_VERSION AS APPLI_VERSION_SP_ASS," 
			+ "SP_ASS.START_DATE AS START_DATE_SP_ASS," 
			+ "SP_ASS.END_DATE AS END_DATE_SP_ASS," 
			+ "SP_ASS.ESTIMATED_END_DATE AS ESTIMATED_END_DATE_SP_ASS \n";
	protected final String FROM_SQL="FROM "+TABLE;
	protected static final String JOIN_REPORTER = " INNER JOIN T_USER US_RE ON US_RE.ID = SP.REPORTER_ID";
	protected static final String JOIN_PROJECT = " INNER JOIN T_PROJECT PR ON PR.ID = SP.PROJECT_ID";
	
	protected String getMaxId() {
		StringBuilder sb = new StringBuilder(SELECT_MAX_ID);
		sb.append(FROM_SQL);	
		
		return sb.toString();
	}
	
	protected String getJoinSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractUserRequest.REPORTER_COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.COLUMNS_SQL);
		sb.append(FROM_SQL);	
		sb.append(JOIN_REPORTER);	
		sb.append(JOIN_PROJECT);	
		if (whereSql !=null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
