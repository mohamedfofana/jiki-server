package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.service.TeamService;

@RestController
@RequestMapping(path = "/api/team", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
	@Autowired
	TeamService teamService;


	@PostMapping("/")
	public Team postTeam(@Valid @RequestBody Team team){
		return teamService.postTeam(team);
	}

	@GetMapping("/all")
	public List<Team> getTeams(){
		return teamService.getTeams();
	}

	@GetMapping("/{id}")
	public Team getTeamById(@PathVariable("id") Long id){
		return teamService.getTeamById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTeamById(@PathVariable("id") Long id){
		teamService.deleteTeamById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchTeam(@PathVariable("id") Long id, @Valid @RequestBody Team team) {
		teamService.patchTeam(id, team);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateTeam(@PathVariable("id") Long id, @Valid @RequestBody Team team){
		teamService.updateTeam(id, team);
		return ResponseEntity.ok().build();
	}
}
