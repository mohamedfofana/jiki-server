package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public abstract class AbstractRowMapper {
	boolean isColumn(ResultSet rs, String column) throws SQLException {
		 ResultSetMetaData meta = rs.getMetaData();
		final long columnCount = rs.getMetaData().getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			if (column.equalsIgnoreCase(meta.getColumnLabel(i))) {
				return true;
			}
		}
		return false;
	}
	boolean isIdColumn(ResultSet rs, String column) throws SQLException {
		if (isColumn(rs, column) && rs.getLong(column)>0) {
			return true;
		}
		return false;
	}
}
