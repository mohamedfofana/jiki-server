package com.kodakro.jiki.repository.request;

public class AbstractCategoryRequest {
	protected final static String TABLE = "T_CATEGORY";
	public static final String COLUMNS_SQL = " "
			+ "ID," 
			+ "PROJECT_ID," 
			+ "TITLE \n";
	protected final String FROM_SQL="FROM "+TABLE;
	
	protected String getMaxId() {
		StringBuilder sb = new StringBuilder(RequestHelper.SELECT_MAX_ID);
		sb.append(FROM_SQL);	
		
		return sb.toString();
	}
	
	protected String getNoJointSelect(String whereSql) {
		StringBuilder sb = new StringBuilder(RequestHelper.SELECT_SQL);		
		sb.append(COLUMNS_SQL);
		sb.append(FROM_SQL);	
		if (whereSql != null) {
			sb.append(whereSql);
		}
		return sb.toString();
	}
	
}
