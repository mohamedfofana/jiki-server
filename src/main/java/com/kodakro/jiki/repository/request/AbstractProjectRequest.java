package com.kodakro.jiki.repository.request;

public abstract class AbstractProjectRequest {
	protected final static String TABLE = "T_PROJECT PR";
	protected static final String SELECT_MAX_ID = "SELECT MAX(ID) ";
	protected static final String SELECT_SQL = "SELECT ";
	public static final String COLUMNS_SQL = " "
		+ "PR.ID AS ID_PR," 
		+ "PR.BACKLOG_ID AS BACKLOG_ID_PR," 
		+ "PR.SHORTNAME AS SHORTNAME_PR," 
		+ "PR.NAME AS NAME_PR," 
		+ "PR.DESCRIPTION AS DESCRIPTION_PR,"
		+ "PR.STATUS AS STATUS_PR,"
		+ "PR.CREATION_DATE AS CREATION_DATE_PR,"
		+ "PR.END_DATE AS END_DATE_PR," 
		+ "PR.UPDATE_DATE AS UPDATE_DATE_PR \n";
	public static final String ASSIGNED_COLUMNS_SQL = " "
			+ "PR_ASS.ID AS ID_PR_ASS," 
			+ "PR_ASS.NAME AS NAME_PR_ASS," 
			+ "PR_ASS.DESCRIPTION AS DESCRIPTION_PR_ASS \n";
	protected final String FROM_SQL="FROM "+TABLE;
	protected static final String JOIN_BACKLOG = " INNER JOIN T_BACKLOG BA ON BA.ID=PR.BACKLOG_ID";
	protected static final String JOIN_TEAM = " INNER JOIN T_TEAM TE ON TE.ID=PR.TEAM_ID";
	
	protected String getMaxId() {
		StringBuilder sb = new StringBuilder(SELECT_MAX_ID);
		sb.append(FROM_SQL);	
		
		return sb.toString();
	}
	
	protected String getExists(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
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
		sb.append(AbstractBacklogRequest.COLUMNS_SQL);
		sb.append(", ");
		sb.append(AbstractTeamRequest.COLUMNS_SQL);
		sb.append(FROM_SQL);		
		sb.append(JOIN_BACKLOG);
		sb.append(JOIN_TEAM);
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
