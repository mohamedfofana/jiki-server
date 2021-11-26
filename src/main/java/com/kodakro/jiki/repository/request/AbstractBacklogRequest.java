package com.kodakro.jiki.repository.request;

public abstract class AbstractBacklogRequest {
	protected final static String TABLE = "T_BACKLOG BA";
	protected static final String SELECT_SQL = "SELECT ";
	public static final String COLUMNS_SQL = " "
			+ "BA.ID AS ID_BA," 
			+ "BA.TITLE AS TITLE_BA," 
			+ "BA.DESCRIPTION AS DESCRIPTION_BA,"
			+ "BA.STATUS AS STATUS_BA,"
			+ "BA.CREATION_DATE AS CREATION_DATE_BA,"
			+ "BA.UPDATE_DATE AS UPDATE_DATE_BA \n";
	public static final String ASSIGNED_COLUMNS_SQL = " "
		+ "BA_ASS.ID AS ID_BA_ASS," 
		+ "BA_ASS.TITLE AS TITLE_BA_ASS," 
		+ "BA_ASS.DESCRIPTION AS DESCRIPTION_BA_ASS,"
		+ "BA_ASS.STATUS AS STATUS_BA_ASS \n";
	protected final String FROM_SQL="FROM "+TABLE;
	
	protected String getJoinSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(FROM_SQL);		
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
