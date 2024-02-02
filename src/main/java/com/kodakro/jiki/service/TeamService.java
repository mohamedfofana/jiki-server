	package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.repository.TeamRepository;

@Service
public class TeamService {
	
	@Autowired
	TeamRepository teamRepository;

	public List<Team> findAll(){
		return teamRepository.findAll();
	}

	public List<Team> findAllAvailableForProject(Long projectId){
		return teamRepository.findAllAvailableForProject(projectId);
	}

	public Team create(Team team) {
		Optional<Team> dbTeam = teamRepository.exists(team.getId());
		if (dbTeam.isPresent()) {
			return null;
		}
		return teamRepository.create(team);
	}
	
	public Team update(Team team){
		Team dbTeam =  teamRepository.exists(team.getId()).orElseThrow(() -> new ResourceNotFoundException("update", "Team", "id", team.getId()));
		if (dbTeam!=null) {
			if(team.getName() != null)
				dbTeam.setName(team.getName());
			if(team.getStatus() != null)
				dbTeam.setStatus(team.getStatus());
			if(team.getUpdateDate() != null)
				dbTeam.setUpdateDate(team.getUpdateDate());
		}
		teamRepository.update(dbTeam);
		return dbTeam;
	}
	
	public boolean deleteById(Long id){
		return teamRepository.deleteById(id);
	}
}
