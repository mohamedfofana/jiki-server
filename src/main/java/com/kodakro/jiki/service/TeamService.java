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

	public Team postTeam(Team team){
		return teamRepository.create(team);
	}

	public List<Team> getTeams(){
		return teamRepository.findAll();
	}

	public Team getTeamById(Long id){
		Optional<Team> team= teamRepository.findById(id);
		if (team.isPresent())
			return team.get();
		else
			return null;
	}

	public void deleteTeamById(Long id){
		teamRepository.deleteById(id);
	}

	public void patchTeam(Long id, Team team) {
		Team dbTeam= teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
		if (dbTeam!=null) {
			if (team.getName() != null)
				dbTeam.setName(team.getName());
			if (team.getStatus() != null)
				dbTeam.setStatus(team.getStatus());
			teamRepository.update(dbTeam);
		}
	}

	public void updateTeam(Long id, Team team){
		Team dbTeam =  teamRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
		if (dbTeam!=null) {
			dbTeam.setName(team.getName());
			dbTeam.setStatus(team.getStatus());
		}
		teamRepository.update(dbTeam);
	}
}
