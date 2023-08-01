package com.kodakro.jiki.repository.intrf;

import java.util.List;

import com.kodakro.jiki.model.Team;

public interface ITeamRepository {

	List<Team> findAllAvailableForProject(Long projectId);
	
}
