package com.kodakro.jiki.repository.intrf;

import com.kodakro.jiki.model.Project;

public interface IProjectRepository {

	/*
	 * find by team
	 */
	Project findByTeam(Long id);
	
}
