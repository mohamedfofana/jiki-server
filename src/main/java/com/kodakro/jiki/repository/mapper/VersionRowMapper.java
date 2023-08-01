package com.kodakro.jiki.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Version;

public class VersionRowMapper extends AbstractRowMapper implements  RowMapper<Version>{
	
	@Override
	public Version mapRow(ResultSet rs, int rowNum) throws SQLException {
		Version version = new Version();
		version.setId(rs.getLong("ID"));
		version.setVersion(rs.getString("VERSION"));
		Project project = new Project();
		project.setId(rs.getLong("PROJECT_ID"));
		version.setProject(project);
		
		return version;
	}
}
