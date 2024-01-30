package com.kodakro.jiki.repository;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kodakro.jiki.model.Version;
import com.kodakro.jiki.repository.intrf.IGenericRepository;
import com.kodakro.jiki.repository.intrf.IVersionRepository;
import com.kodakro.jiki.repository.mapper.VersionRowMapper;
import com.kodakro.jiki.repository.request.AbstractVersionRequest;

@Repository
public class VersionRepository extends AbstractVersionRequest implements IGenericRepository<Version>, IVersionRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Value("${sql.version.insert}")
	private String sqlInsert;
	
	@Override
	public List<Version> findByProject(Long projectId) {
		final String whereSql= " WHERE PROJECT_ID =? ";
		Object[] param = {projectId};
		int[] types = {Types.INTEGER};
		List<Version> versions = jdbcTemplate.query(getNoJointSelect(whereSql), param, types,
				new VersionRowMapper());
		
		return versions;
	}

	@Override
	public Version create(Version version) {
		version.setId(maxId()+1);

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection
					.prepareStatement(sqlInsert);
			ps.setLong(1, version.getId());
			ps.setLong(2, version.getProject().getId());
			ps.setString(3, version.getVersion());
			
			return ps;
		});
		
		return version;
	}

	@Override
	public List<Version> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long maxId() {
		final Long maxId = jdbcTemplate.queryForObject(getMaxId(), null, null, Long.class );
		return maxId!=null?maxId:1;
	}

	@Override
	public Optional<Version> exists(Long id) {
		final String whereSql= " WHERE ID =? ";
		Object[] param = {id};
		int[] types = {Types.INTEGER};
		Version version = null;
		try {
			version = jdbcTemplate.queryForObject(getNoJointSelect(whereSql), param, types,
					new VersionRowMapper());
		}catch (EmptyResultDataAccessException e) {
			// log no user found
		}
		return Optional.ofNullable(version);
	}

	@Override
	public Optional<Version> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Version t) {
		// TODO Auto-generated method stub
		return false;
	}

}
