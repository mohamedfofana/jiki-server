package com.kodakro.jiki.repository.request;

public abstract class AbstractUserRequest {
	protected final static String TABLE = "T_USER US";
	protected static final String SELECT_SQL = "SELECT ";
	public static final String AUTH_COLUMNS_SQL = " "
			+ "US.USERNAME AS USERNAME_US," 
			+ "US.ROLE AS ROLE_US,"
			+ "US.PASSWORD AS PASSWORD_US \n";
	public static final String COLUMNS_SQL = " "
		+ "US.ID AS ID_US," 
		+ "US.TEAM_ID AS TEAM_ID_US," 
		+ "US.FIRSTNAME AS FIRSTNAME_US," 
		+ "US.LASTNAME AS LASTNAME_US," 
		+ "US.USERNAME AS USERNAME_US," 
		+ "US.PASSWORD AS PASSWORD_US," 
		+ "US.STATUS AS STATUS_US,"
		+ "US.ROLE AS ROLE_US,"
		+ "US.CREATION_DATE AS CREATION_DATE_US,"
		+ "US.UPDATE_DATE AS UPDATE_DATE_US \n";
	public static final String REPORTER_COLUMNS_SQL = " "
			+ "US_RE.ID AS ID_US_RE," 
			+ "US_RE.FIRSTNAME AS FIRSTNAME_US_RE," 
			+ "US_RE.LASTNAME AS LASTNAME_US_RE,"
			+ "US_RE.ROLE AS ROLE_US_RE \n";
	public static final String ASSIGNED_COLUMNS_SQL = " "
			+ "US_ASS.ID AS ID_US_ASS," 
			+ "US_ASS.FIRSTNAME AS FIRSTNAME_US_ASS," 
			+ "US_ASS.LASTNAME AS LASTNAME_US_ASS,"
			+ "US_ASS.ROLE AS ROLE_US_ASS \n";
	protected final String FROM_SQL="FROM "+TABLE;
	protected static final String JOIN_TEAM = " INNER JOIN T_TEAM TE ON TE.ID=US.TEAM_ID";
	protected static final String JOIN_PROJECT = " INNER JOIN T_PROJECT PR_ASS ON PR_ASS.ID=US.PROJECT_ID";
	
	protected String getJoinSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractProjectRequest.ASSIGNED_COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_TEAM);
		sb.append(JOIN_PROJECT);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	protected String getJoinSelectForAuth(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);	
		sb.append(AUTH_COLUMNS_SQL);
		sb.append(FROM_SQL);	
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
