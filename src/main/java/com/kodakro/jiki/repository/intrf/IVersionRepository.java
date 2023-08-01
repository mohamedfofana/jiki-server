package com.kodakro.jiki.repository.intrf;

import java.util.List;

import com.kodakro.jiki.model.Version;

public interface IVersionRepository {

	List<Version> findByProject(Long projectId);
	Version create(Version version);
}
