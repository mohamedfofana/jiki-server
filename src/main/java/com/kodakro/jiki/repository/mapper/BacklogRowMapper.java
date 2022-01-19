package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Backlog;

public class BacklogRowMapper implements RowMapper<Backlog>{
	@Override
	public Backlog mapRow(ResultSet rs, int rowNum) throws SQLException {
		Backlog backlog = new Backlog();
		backlog.setId(rs.getLong("ID_BA"));
		backlog.setTitle(rs.getString("TITLE_BA"));
		backlog.setDescription(rs.getString("DESCRIPTION_BA"));
		backlog.setStatus(rs.getString("STATUS_BA"));
		backlog.setCreationDate(rs.getTimestamp("CREATION_DATE_BA"));
		backlog.setUpdateDate(rs.getTimestamp("UPDATE_DATE_BA"));
		
		return backlog;
	}
	
	public Backlog mapRowAssigned(ResultSet rs, int rowNum) throws SQLException {
		Backlog backlog = new Backlog();
		backlog.setId(rs.getLong("ID_BA_ASS"));
		backlog.setTitle(rs.getString("TITLE_BA_ASS"));
		backlog.setDescription(rs.getString("DESCRIPTION_BA_ASS"));
		
		return backlog;
	}
}
