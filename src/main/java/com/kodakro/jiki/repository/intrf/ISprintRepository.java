package com.kodakro.jiki.repository.intrf;

import java.util.List;
import java.util.Optional;

import com.kodakro.jiki.model.Sprint;

public interface ISprintRepository {

	/*
	 * Find the running sprint in the project
	 */
	Optional<Sprint> findCurrentByProjectId(Long id);

	/*
	 * find sprints by project
	 */
	List<Sprint> findByProjectId(Long id);
	
}
