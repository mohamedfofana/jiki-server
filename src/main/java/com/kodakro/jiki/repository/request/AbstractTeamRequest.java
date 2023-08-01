package com.kodakro.jiki.repository.request;

public abstract class AbstractTeamRequest {
	protected final static String TABLE = "T_TEAM TE";
	protected static final String SELECT_MAX_ID = "SELECT MAX(ID) ";
	protected static final String SELECT_SQL = "SELECT ";
	public static final String COLUMNS_SQL = " "
		+ "TE.ID AS ID_TE," 
		+ "TE.NAME AS NAME_TE," 
		+ "TE.DESCRIPTION AS DESCRIPTION_TE," 
		+ "TE.STATUS AS STATUS_TE,"
		+ "TE.CREATION_DATE AS CREATION_DATE_TE,"
		+ "TE.UPDATE_DATE AS UPDATE_DATE_TE \n";
	public static final String ASSIGNED_COLUMNS_SQL = " "
			+ "TE_ASS.ID AS ID_TE_ASS," 
			+ "TE_ASS.NAME AS NAME_TE_ASS," 
			+ "TE_ASS.STATUS AS STATUS_TE_ASS,"
			+ "TE_ASS.CREATION_DATE AS CREATION_DATE_TE_ASS,"
			+ "TE_ASS.UPDATE_DATE AS UPDATE_DATE_TE_ASS \n";
	public static final String OF_PROJECT_COLUMNS_SQL = " "
			+ "TE_PR_ASS.ID AS ID_TE_PR_ASS," 
			+ "TE_PR_ASS.NAME AS NAME_TE_PR_ASS," 
			+ "TE_PR_ASS.STATUS AS STATUS_TE_PR_ASS,"
			+ "TE_PR_ASS.CREATION_DATE AS CREATION_DATE_TE_PR_ASS,"
			+ "TE_PR_ASS.UPDATE_DATE AS UPDATE_DATE_TE_PR_ASS \n";
	protected final String FROM_SQL="FROM "+TABLE;
	protected static final String LEFT_JOIN_PROJECT = " LEFT JOIN T_PROJECT PR ON PR.TEAM_ID=TE.ID";
	
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
	
	protected String getSelect() {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(FROM_SQL);	
		return sb.toString();
	}

	protected String getJoinSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(FROM_SQL);	
		if (whereSql !=null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
