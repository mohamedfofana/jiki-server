package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Team;
import com.kodakro.jiki.service.TeamService;

@RestController
@RequestMapping(path = "/api/team", produces = MediaType.APPLICATION_JSON_VALUE)
public class TeamController {
	@Autowired
	TeamService teamService;

	@GetMapping("/all")
	public List<Team> findAll(){
		return teamService.findAll();
	}

	@GetMapping("/allAvailableForProject/{id}")
	public List<Team> findAllAvailableForProject(@PathVariable("id") Long id){
		return teamService.findAllAvailableForProject(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> register(@Valid @RequestBody Team team) {
		final Team dbTeam = teamService.create(team);
		if (dbTeam == null) {
			return new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("KO", null, "Team  "+ team.getName() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("OK", dbTeam, "Team created"), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Team team){
		final Team dbTeam = teamService.update(team);
		if (dbTeam == null) {
			return new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("KO", null, "Unable to uppdate team "+ team.getName() + "."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("OK", dbTeam, "Team updated"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		final boolean status = teamService.deleteById(id);
		if(status) {
			return new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("OK", null, "Team deleted"), HttpStatus.OK);
		}
		return  new ResponseEntity<CustomResponseType<Team>>(new CustomResponseType<Team>("KO", null, "Unable to delete team with id = "+ id + "."), HttpStatus.CONFLICT);
	}


}
